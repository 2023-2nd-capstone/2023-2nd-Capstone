package capstone.doAds.config.oauth;

import capstone.doAds.auth.PrincipalDetails;
import capstone.doAds.auth.SecurityUtils;
import capstone.doAds.config.oauth.provider.GoogleUserInfo;
import capstone.doAds.config.oauth.provider.OAuth2UserInfo;
import capstone.doAds.domain.Authority;
import capstone.doAds.domain.Member;
import capstone.doAds.domain.YoutubeProfile;
import capstone.doAds.repository.MemberRepository;
import capstone.doAds.repository.YoutubeProfileRepository;
import capstone.doAds.service.YoutubeApiService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.services.youtube.model.Channel;
import com.google.api.services.youtube.model.ThumbnailDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;
    private final YoutubeApiService youtubeApiService;
    private final YoutubeProfileRepository youtubeProfileRepository;

    private final OAuth2AuthorizedClientService authorizedClientService;

    public String getAccessToken(String principalName, String clientRegistrationId) {
        OAuth2AuthorizedClient authorizedClient = this.authorizedClientService.loadAuthorizedClient(clientRegistrationId, principalName);

        if (authorizedClient == null) {
            throw new IllegalArgumentException("No client with registration id " + clientRegistrationId + " for principal " + principalName);
        }

        OAuth2AccessToken accessToken = authorizedClient.getAccessToken();

        if (accessToken == null) {
            throw new IllegalArgumentException("No access token for client with registration id " + clientRegistrationId + " for principal " + principalName);
        }

        System.out.println("accessToken = " + accessToken.getTokenValue());

        return accessToken.getTokenValue();
    }

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        OAuth2UserInfo oAuth2UserInfo = null;
        if(userRequest.getClientRegistration().getRegistrationId().equals("google")){
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        }
        String email = oAuth2UserInfo.getEmail();
        Optional<Member> member = memberRepository.findByEmail(email);
        if (member.isEmpty()) {
            Member member1 = oAuthJoin(oAuth2UserInfo);
            OAuth2User result = new PrincipalDetails(member1, oAuth2User.getAttributes());
            CompletableFuture.runAsync(() -> setYoutubeProfile());
            return result;
        }

        OAuth2User result = new PrincipalDetails(member.get(), oAuth2User.getAttributes());

        CompletableFuture.runAsync(() -> setYoutubeProfile());

        return result;
    }
    @Transactional
    public Member oAuthJoin(OAuth2UserInfo userInfo) {
        Member member = Member.builder()
                .nickname(userInfo.getName())
                .password(UUID.randomUUID().toString())
                .email(userInfo.getEmail())
                .authority(Authority.ROLE_INFLUENCER)
                .build();
        memberRepository.save(member);
        return member;
    }

    @Transactional
    public void setYoutubeProfile() {
        Member member = memberRepository.findByEmail(SecurityUtils.getLoggedUserEmail()).get();
        String accessToken = getAccessToken(SecurityUtils.getLoggedUserEmail(), "google");
        List<Channel> channelData = null;
        try {
            channelData = youtubeApiService.getChannelData(getChannelId(accessToken));
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        Channel channel = channelData.get(0);
        System.out.println("channel = " + channel);
        String title = channel.getSnippet().getTitle();
        String description = channel.getSnippet().getDescription();
        BigInteger subscriberCount = channel.getStatistics().getSubscriberCount();
        ThumbnailDetails thumbnails = channel.getSnippet().getThumbnails();
        String imageUrl = thumbnails.getDefault().getUrl();
        YoutubeProfile youtubeProfile = new YoutubeProfile(title, description, subscriberCount, imageUrl);
        youtubeProfileRepository.save(youtubeProfile);
        member.getProfile().setYoutubeProfile(youtubeProfile);
        memberRepository.save(member);
    }

    @Transactional
    public String getChannelId(String accessToken) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        ResponseEntity<String> response = restTemplate.exchange("https://www.googleapis.com/youtube/v3/channels?part=id&mine=true", HttpMethod.GET, entity, String.class);
        String responseBody = response.getBody();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(responseBody);
        JsonNode items = root.path("items");
        return items.get(0).path("id").asText();
    }
}

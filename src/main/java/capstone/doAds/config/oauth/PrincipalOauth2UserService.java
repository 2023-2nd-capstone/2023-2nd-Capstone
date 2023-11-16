package capstone.doAds.config.oauth;

import capstone.doAds.auth.PrincipalDetails;
import capstone.doAds.config.oauth.provider.GoogleUserInfo;
import capstone.doAds.config.oauth.provider.OAuth2UserInfo;
import capstone.doAds.domain.Authority;
import capstone.doAds.domain.Member;
import capstone.doAds.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private MemberRepository memberRepository;

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
            return new PrincipalDetails(member1, oAuth2User.getAttributes());
        }
        return new PrincipalDetails(member.get(), oAuth2User.getAttributes());
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
}

package capstone.doAds.service;

import capstone.doAds.auth.SecurityUtils;
import capstone.doAds.domain.Member;
import capstone.doAds.domain.Profile;
import capstone.doAds.domain.ProfileTag;
import capstone.doAds.domain.Tag;
import capstone.doAds.dto.FeedDto;
import capstone.doAds.dto.InfluencerProfileModifyResponseDto;
import capstone.doAds.dto.InfluencerProfileResponseDto;
import capstone.doAds.dto.NicknameSearchResponseDto;
import capstone.doAds.exception.NotFoundException;
import capstone.doAds.exception.UnauthorizedException;
import capstone.doAds.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final YoutubeProfileRepository youtubeProfileRepository;
    private final YoutubeApiService youtubeApiService;
    private final MemberRepository memberRepository;
    private final TagRepository tagRepository;
    private final ProfileTagRepository profileTagRepository;

    public InfluencerProfileResponseDto getInfluencerProfile(Long profileId) {
        Optional<Member> member = memberRepository.findByEmailFetchProfile(SecurityUtils.getLoggedUserEmail());
        Profile profile = profileRepository.findById(profileId).orElseThrow(
                () -> new NotFoundException("프로필(아이디: " + profileId + ")를 찾을 수 없습니다."));
        if (member.isEmpty()) {
            return profile.getInfluencerProfile(false);
        }
        return profile.getInfluencerProfile(member.get().getProfile().equals(profile));
    }

    public InfluencerProfileResponseDto getMyProfile() {
        Profile profile = memberRepository.findByEmailFetchProfile(SecurityUtils.getLoggedUserEmail()).orElseThrow(
                () -> new UnauthorizedException("로그인이 필요합니다.")).getProfile();
        return profile.getInfluencerProfile(true);
    }

    @Transactional
    public void modifyMyProfile(Long profileId, InfluencerProfileModifyResponseDto influencerProfileModifyResponseDto) {
        Profile profile = profileRepository.findById(profileId).get();
        profile.resetTags();
        profile.modifyInfluencerProfile(influencerProfileModifyResponseDto);

        for (String tagName : influencerProfileModifyResponseDto.getTags()) {
            Tag tag = tagRepository.findByName(tagName);
            profileTagRepository.save(new ProfileTag(profile, tag));
        }
        profileRepository.save(profile);
    }
  
    public List<NicknameSearchResponseDto> getProfileByNickname(String nickname) {
        List<Profile> profiles = profileRepository.findAllByNickname(nickname);
        return profiles.stream().map(p -> p.getNicknameSearch()).collect(Collectors.toList());
    }

    public List<FeedDto> getFeed() {
        List<Profile> profiles = profileRepository.findAll();
        return profiles.stream()
                .filter(profile -> profile.getYoutubeProfile() != null)
                .map(profile -> new FeedDto(profile))
                .collect(Collectors.toList());
    }

    public List<FeedDto> getFeedByPopular() {
        return profileRepository.findProfileByPopular().stream()
                .filter(profile -> profile.getYoutubeProfile() != null)
                .map(profile -> new FeedDto(profile))
                .collect(Collectors.toList());
    }

    public List<FeedDto> getFeedByTag(String tagName) {
        return profileRepository.findProfileByTagName(tagName).stream()
                .filter(profile -> profile.getYoutubeProfile() != null && profile.getProfileTagNames().contains(tagName))
                .map(profile -> new FeedDto(profile))
                .collect(Collectors.toList());
    }
}

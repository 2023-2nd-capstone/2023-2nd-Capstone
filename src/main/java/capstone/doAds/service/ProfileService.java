package capstone.doAds.service;

import capstone.doAds.auth.SecurityUtils;
import capstone.doAds.domain.Profile;
import capstone.doAds.dto.InfluencerProfileModifyResponseDto;
import capstone.doAds.dto.InfluencerProfileResponseDto;
import capstone.doAds.exception.NotFoundException;
import capstone.doAds.exception.UnauthorizedException;
import capstone.doAds.repository.MemberRepository;
import capstone.doAds.repository.ProfileRepository;
import capstone.doAds.repository.YoutubeProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final YoutubeProfileRepository youtubeProfileRepository;
    private final YoutubeApiService youtubeApiService;
    private final MemberRepository memberRepository;

    public InfluencerProfileResponseDto getInfluencerProfile(Long profileId) {
        Profile profile = profileRepository.findById(profileId).orElseThrow(
                () -> new NotFoundException("프로필(아이디: " + profileId + ")를 찾을 수 없습니다."));
        return profile.getInfluencerProfile();
    }

    public InfluencerProfileResponseDto getMyProfile() {
        Profile profile = memberRepository.findByEmailFetchProfile(SecurityUtils.getLoggedUserEmail()).orElseThrow(
                () -> new UnauthorizedException("로그인이 필요합니다.")).getProfile();
        return profile.getInfluencerProfile();
    }

    @Transactional
    public void modifyMyProfile(Long profileId, InfluencerProfileModifyResponseDto influencerProfileModifyResponseDto) {
        Profile profile = profileRepository.findById(profileId).get();
        profile.modifyInfluencerProfile(influencerProfileModifyResponseDto);
        profileRepository.save(profile);
    }
}

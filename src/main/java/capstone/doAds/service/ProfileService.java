package capstone.doAds.service;

import capstone.doAds.domain.Profile;
import capstone.doAds.dto.InfluencerProfileResponseDto;
import capstone.doAds.exception.NotFoundException;
import capstone.doAds.repository.MemberRepository;
import capstone.doAds.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;

    public InfluencerProfileResponseDto getInfluencerProfile(Long profileId) {
        Profile profile = profileRepository.findById(profileId).orElseThrow(
                () -> new NotFoundException("프로필(아이디: " + profileId + ")를 찾을 수 없습니다."));
        return profile.getInfluencerProfile();
    }
}

package capstone.doAds.controller;

import capstone.doAds.dto.InfluencerProfileResponseDto;
import capstone.doAds.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/profile/{profile_id}")
    public ResponseEntity<InfluencerProfileResponseDto> getInfluencerProfile(@PathVariable("profile_id") Long profileId) {
        return ResponseEntity.ok(profileService.getInfluencerProfile(profileId));
    }
}

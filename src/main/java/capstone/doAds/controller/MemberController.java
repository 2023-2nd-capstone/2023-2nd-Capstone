package capstone.doAds.controller;

import capstone.doAds.dto.InfluencerProfileResponseDto;
import capstone.doAds.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final ProfileService profileService;

    @GetMapping("/my-profile")
    public ResponseEntity<InfluencerProfileResponseDto> findMyProfileById() {
        return ResponseEntity.ok(profileService.getMyProfile());
    }
}

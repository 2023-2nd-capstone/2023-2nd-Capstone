package capstone.doAds.controller;

import capstone.doAds.dto.InfluencerProfileResponseDto;
import capstone.doAds.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/profile/{profile_id}")
    public String getInfluencerProfile(@PathVariable("profile_id") Long profileId, Model model) {
       InfluencerProfileResponseDto influencerProfileResponseDto = profileService.getInfluencerProfile(profileId);
       model.addAttribute("profile", influencerProfileResponseDto);
       return "influencerProfile";
    }
}

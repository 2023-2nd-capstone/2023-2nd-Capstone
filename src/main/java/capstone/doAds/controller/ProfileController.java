package capstone.doAds.controller;

import capstone.doAds.dto.InfluencerProfileModifyResponseDto;
import capstone.doAds.dto.InfluencerProfileResponseDto;
import capstone.doAds.service.LikesService;
import capstone.doAds.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;
    private final LikesService likesService;

    @GetMapping("/profile/{profile_id}")
    public String getInfluencerProfile(@PathVariable("profile_id") Long profileId, Model model) {
       InfluencerProfileResponseDto influencerProfileResponseDto = profileService.getInfluencerProfile(profileId);
       model.addAttribute("profile", influencerProfileResponseDto);
       return "influencerProfile";
    }

    @GetMapping("/profile/{profile_id}/modifyProfile")
    public String modifyProfile(@PathVariable("profile_id") Long profileId, Model model) {
        InfluencerProfileResponseDto influencerProfileResponseDto = profileService.getInfluencerProfile(profileId);
        model.addAttribute("profile", influencerProfileResponseDto);
        return "modifyProfile";
    }

    @PostMapping("/profile/{profile_id}/modifyProfile")
    public String modifyProfile(@PathVariable("profile_id") Long profileId, HttpServletRequest request, Model model) {
        String nickname = request.getParameter("nickname");
        String profileImageUrl = request.getParameter("profileImageUrl");
        String description = request.getParameter("description");
        InfluencerProfileModifyResponseDto influencerProfileModifyResponseDto = new InfluencerProfileModifyResponseDto(nickname, profileImageUrl, description);
        profileService.modifyMyProfile(profileId, influencerProfileModifyResponseDto);
        return "redirect:/profile/" + profileId;
    }

    @PostMapping("/profile/{profile_id}/likes")
    public ResponseEntity<String> likeProfile(@PathVariable("profile_id") Long profileId, Model model) {
        boolean liked = likesService.like(profileId);
        return ResponseEntity.ok(liked ? "Liked" : "Unliked");
    }
}

package capstone.doAds.controller;

import capstone.doAds.dto.FeedDto;
import capstone.doAds.dto.InfluencerProfileModifyResponseDto;
import capstone.doAds.dto.InfluencerProfileResponseDto;
import capstone.doAds.dto.NicknameSearchResponseDto;
import capstone.doAds.service.LikesService;
import capstone.doAds.service.ProfileService;
import capstone.doAds.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;
    private final LikesService likesService;
    private final TagService tagService;

    @GetMapping("/profile/{profile_id}")
    public String getInfluencerProfile(@PathVariable("profile_id") Long profileId, Model model) {
       InfluencerProfileResponseDto influencerProfileResponseDto = profileService.getInfluencerProfile(profileId);
       model.addAttribute("profile", influencerProfileResponseDto);
       return "influencerProfile";
    }

    @GetMapping("/profile/{profile_id}/modifyProfile")
    public String modifyProfile(@PathVariable("profile_id") Long profileId, Model model) {
        InfluencerProfileResponseDto influencerProfileResponseDto = profileService.getInfluencerProfile(profileId);
        List<String> tagNames = tagService.getTagNames();
        model.addAttribute("tagNames", tagNames);
        model.addAttribute("profile", influencerProfileResponseDto);
        return "modifyProfile";
    }

    @PostMapping("/profile/{profile_id}/modifyProfile")
    public String modifyProfile(@PathVariable("profile_id") Long profileId,
                                @RequestParam("nickname") String nickname,
                                @RequestParam("profileImageUrl") String profileImageUrl,
                                @RequestParam("description") String description,
                                @RequestParam(value = "profileTagNames", required = false) List<String> selectedTags) {
        if (selectedTags == null) selectedTags = new ArrayList<>();
        InfluencerProfileModifyResponseDto influencerProfileModifyResponseDto = new InfluencerProfileModifyResponseDto(nickname, profileImageUrl, description, selectedTags);
        profileService.modifyMyProfile(profileId, influencerProfileModifyResponseDto);
        return "redirect:/profile/" + profileId;
    }

    @PostMapping("/profile/{profile_id}/likes")
    public String likeProfile(@PathVariable("profile_id") Long profileId, Model model) {
        likesService.like(profileId);
        return "redirect:/profile/" + profileId;
    }

    @GetMapping("/")
    public String mainFeed(@RequestParam(name = "sortBy", defaultValue = "date") String sortBy,
                           @RequestParam(name = "tag", required = false) String tag,
                           Model model) {
        List<FeedDto> feed;
        if ("popularity".equals(sortBy)) {
            feed = profileService.getFeedByPopular();
        } else if ("tag".equals(sortBy) && tag != null) {
            feed = profileService.getFeedByTag(tag);
        } else {
            feed = profileService.getFeed();
        }
        List<String> tagNames = tagService.getTagNames();
        model.addAttribute("feed", feed);
        model.addAttribute("tagNames", tagNames);
        return "feed";
    }

    @GetMapping("/search")
    public String showSearchForm() {
        return "nicknameSearchForm";
    }


    @PostMapping("/search")
    public String getProfileByNickname(@RequestParam("nickname") String nickname, Model model) {
        List<NicknameSearchResponseDto> searchResults = profileService.getProfileByNickname(nickname);

        model.addAttribute("searchResults", searchResults);
        model.addAttribute("searchQuery", nickname);

        return "nicknameSearchResult";
    }
}

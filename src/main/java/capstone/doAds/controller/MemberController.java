package capstone.doAds.controller;

import capstone.doAds.auth.SecurityUtils;
import capstone.doAds.config.oauth.PrincipalOauth2UserService;
import capstone.doAds.domain.Member;
import capstone.doAds.dto.InfluencerProfileResponseDto;
import capstone.doAds.dto.JoinDto;
import capstone.doAds.repository.MemberRepository;
import capstone.doAds.service.MemberService;
import capstone.doAds.service.ProfileService;
import capstone.doAds.service.YoutubeApiService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final ProfileService profileService;
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MemberService memberService;
    private final YoutubeApiService youtubeApiService;
    private final PrincipalOauth2UserService principalOauth2UserService;

    @GetMapping("/myPage")
    public String home(Model model) throws JsonProcessingException {
        String loggedUserEmail = SecurityUtils.getLoggedUserEmail();
        Optional<Member> member = memberRepository.findByEmail(loggedUserEmail);
        if (!member.isEmpty()) {
            model.addAttribute("nickname", member.get().getNickname());
            model.addAttribute("profileId", member.get().getProfile().getId());
        }
        return "index";
    }

    @GetMapping("/loginForm")
    public String login() {
        return "loginForm";
    }

    @GetMapping("/joinForm")
    public String joinForm() {
        return "joinForm";
    }

    @PostMapping("/join")
    public String join(JoinDto joinDto) {
        memberService.join(joinDto);
        return "redirect:/loginForm";
    }

    @GetMapping("/myProfile")
    public String getMyProfile(Model model) {
        InfluencerProfileResponseDto influencerProfileResponseDto = profileService.getMyProfile();
        model.addAttribute("myProfile", influencerProfileResponseDto);
        return "myProfile";
    }

    @GetMapping("/channelData")
    public void getChannelData(@AuthenticationPrincipal OAuth2User principal, Model model) {
        String channelId = principal.getAttribute("sub");
        System.out.println("channelId = " + channelId);
    }
}
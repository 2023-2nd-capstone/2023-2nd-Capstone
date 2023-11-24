package capstone.doAds.controller;

import capstone.doAds.auth.SecurityUtils;
import capstone.doAds.domain.Member;
import capstone.doAds.dto.InfluencerProfileResponseDto;
import capstone.doAds.dto.JoinDto;
import capstone.doAds.repository.MemberRepository;
import capstone.doAds.service.MemberService;
import capstone.doAds.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    @GetMapping("/myPage")
    public String home(Model model) {
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
}
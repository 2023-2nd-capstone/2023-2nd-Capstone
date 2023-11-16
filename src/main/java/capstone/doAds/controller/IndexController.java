package capstone.doAds.controller;

import capstone.doAds.auth.PrincipalDetails;
import capstone.doAds.auth.SecurityUtils;
import capstone.doAds.domain.Authority;
import capstone.doAds.domain.Member;
import capstone.doAds.dto.JoinDto;
import capstone.doAds.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class IndexController {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping({"", "/"})
    public String index(){
        return "index";
    }

    @GetMapping("/user")
    public String user() {
        return "user";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/manager")
    public String manager() {
        return "manager";
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
        Member member = Member.builder()
                .nickname(joinDto.getNickname())
                .email(joinDto.getEmail())
                .password(bCryptPasswordEncoder.encode(joinDto.getPassword()))
                .authority(Authority.ROLE_USER)
                .build();
        memberRepository.save(member);
        return "redirect:/loginForm";
    }

    @GetMapping("/info")
    public @ResponseBody String info() {
        return "개인정보";
    }

    @GetMapping("/user/test")
    public @ResponseBody String test() {
        Member member = memberRepository.findByEmail(SecurityUtils.getLoggedUserEmail()).orElseThrow(() -> new IllegalArgumentException("ㅇㅅㅇ"));
        System.out.println(member.getNickname());
        System.out.println(member.getEmail());
        return "회원 정보 출력 성공";
    }
}

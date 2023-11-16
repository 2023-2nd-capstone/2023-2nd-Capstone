package capstone.doAds.service;

import capstone.doAds.domain.Authority;
import capstone.doAds.domain.Member;
import capstone.doAds.dto.JoinDto;
import capstone.doAds.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MemberRepository memberRepository;

    public void join(JoinDto joinDto) {
        Member member = Member.builder()
                .nickname(joinDto.getNickname())
                .email(joinDto.getEmail())
                .password(bCryptPasswordEncoder.encode(joinDto.getPassword()))
                .authority(Authority.ROLE_USER)
                .build();
        memberRepository.save(member);
    }


}

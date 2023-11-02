package capstone.doAds.auth;

import capstone.doAds.domain.Member;
import capstone.doAds.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// 시큐리티 설정에서 loginProcessingUrl("login");
// login 요청이 오면 자동으로 UserDetailsService 타입으로 IoC되어 있는 loadUserByUsername 함수가 실행
// 이건 규칙임
@Service
public class PrincipalDetailsService implements UserDetailsService {

    @Autowired
    private MemberRepository usersRepository;

    // 시큐리티 session(내부 Authentication(내부 UserDetails))
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member users = usersRepository.findByEmail(username);
        if (users != null) {
            return new PrincipalDetails(users);
            // 리턴될 때 시큐리티 session(내부 Authentication(내부 UserDetails)) 이런식으로
            // 값이 들어가고, 로그인 처리가 됨
        }
        return null;
    }
}

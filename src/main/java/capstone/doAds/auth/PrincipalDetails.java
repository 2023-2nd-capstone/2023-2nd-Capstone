package capstone.doAds.auth;
//  -> 로그인이 진행이 완료가 되면 시큐리티 세션(Security ContextHolder)를 생성.
//  -> Security ContextHolder에 들어갈 수 있는 오브젝트는 Authentication 객체로 정해져있음
//  -> Authentication 안에 User정보가 있어야 됨
//  -> User오브젝트 타입 => UserDetails 타입 오브젝트
//
//  => Security Session => Authentication => UserDetails

import capstone.doAds.domain.Member;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Data
// PrincipalDetails를 UserDetails로 implements함으로써 Authentication에 들어갈 수 있는 타입으로 바뀜
public class PrincipalDetails implements UserDetails, OAuth2User {

    private Member users;
    private Map<String, Object> attributes;

    public PrincipalDetails(Member users) {
        this.users = users;
    }

    public PrincipalDetails(Member users, Map<String, Object> attributes) {
        this.users = users;
        this.attributes = attributes;
    }
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    // 해당 User의 권한을 리턴하는 곳
   @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return users.getAuthority().toString();
            }
        });
        return collect;
    }

    @Override
    public String getPassword() {
        return users.getPassword();
    }

    @Override
    public String getUsername() {
        return users.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 비밀번호 오래 사용했는지 안했는지
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 로그인 했는지 안했는지
    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return null;
    }
}

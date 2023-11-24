package capstone.doAds.config;


import capstone.doAds.auth.PrincipalDetailsService;
import capstone.doAds.config.oauth.CustomAuthenticationSuccessHandler;
import capstone.doAds.config.oauth.PrincipalOauth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록됨
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PrincipalOauth2UserService principalOauth2UserService;

    @Autowired
    private PrincipalDetailsService principalDetailsService;

    @Bean
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .anyRequest().permitAll();
        http.formLogin().loginPage("/login").defaultSuccessUrl("/", true);
        // 로그인이 수행될 uri 매핑 (post 요청이 기본)
        http.formLogin().loginProcessingUrl("/login").defaultSuccessUrl("/", true);
        // 인증된 사용자이지만 인가되지 않은 경로에 접근시 리다이랙팅 시킬 uri 지정
        http.exceptionHandling().accessDeniedPage("/forbidden");
        // logout
        http.logout().logoutUrl("/logout").logoutSuccessUrl("/");

        http.userDetailsService(principalDetailsService);
        http
                .authorizeRequests()
                // ...
                .and()
                .oauth2Login()
                .loginPage("/loginForm")
                .userInfoEndpoint()
                .userService(principalOauth2UserService)
                .and()
                .successHandler(new CustomAuthenticationSuccessHandler(principalOauth2UserService));
        // 구글 로그인 후 후처리가 필요함. 1. 코드받기(인증) 2. 엑세스토큰(권한)
        // 3. 사용자프로필 정보 가져오고 4. 그 정보를 토대로 회원가입을 자동으로 진행시키기도 함.
        // if 구글 프로필 정보로 회원가입 정보가 부족할 시 따로 구현해야함
    }
}

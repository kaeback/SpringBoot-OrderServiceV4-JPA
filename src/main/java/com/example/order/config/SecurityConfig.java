package com.example.order.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationFailureHandler authenticationFailureHandler;
    private final PrincipalOAuthUserService principalOAuthUserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Cross-site request forgery 보호 기능
                .csrf().disable()
                // iframe으로 접근이 안되도록 하는 설정을 비활성화, sameOrigin()은 같은 도메인 내에서는 접근 가능
                .headers().frameOptions().disable()
                .and()
                    // URL별 권한접근 제어 정의
                    .authorizeRequests()
                    // permitAll() : antMatchers에서 설정한 리소스의 접근을 인증절차 없이 허용
                    .antMatchers("/", "/member/join", "/member/login", "/member/login-failed", "member/logout").permitAll()
                    .antMatchers("/v2/api-docs", "/swagger*/**").permitAll()
                    .antMatchers("/css/*", "/favicon.ico").permitAll()
                    .antMatchers("/api/**").permitAll()
                    // admin 하위의 모든 리소스는 인증 후 ADMIN 권한을 가진 사용자만 접근을 허용
                    .antMatchers("/admin/**").hasAnyRole("ADMIN")
                    .anyRequest().authenticated()
                .and()
                    // 폼 로그인 방식을 사용
                    .formLogin()
                    // username 파라미터 이름 지정 기본값은 username이며 다른 이름 사용시 지정해야 함.
                    .usernameParameter("member_id")
                    // 사용자가 만든 로그인 페이지를 사용함
                    // 설정하지 않으면 디폴트 URL이 “/login”이기 때문에 “/login”로 호출하면 스프링이 제공하는 기본 로그인페이지가 호출된다.
                    .loginPage("/member/login")
                    // 로그인 인증 처리를 하는 URL
                    .loginProcessingUrl("/member/login")
                    // 인증에 성공 했을 때 이동할 URL
                    .defaultSuccessUrl("/member/login-success")
                    // 로그인 실패 후 이동할 URL
//                    .failureUrl("/member/login-failed")
                    // 로그인 실패 핸들러
                    .failureHandler(authenticationFailureHandler)
                .and()
                    .logout()
                    // 로그아웃 URl 지정
                    .logoutUrl("/member/logout")
                    // 로그아웃 성공 후 리다이렉트 주소
                    .logoutSuccessUrl("/")
                    // 로그아웃 후 처리될 내용을 담을 클래스를 지정(logoutSuccessUrl()은 무시된다.)
                    //.logoutSuccessHandler(customLogoutSuccessHandler)
                    // 세션 날리기
                    .invalidateHttpSession(true)
                    // 쿠키 날리기
                    .deleteCookies("JSESSIONID")
                .and()
                    .oauth2Login()
                    .userInfoEndpoint()
                    .userService(principalOAuthUserService);

            return http.build();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}

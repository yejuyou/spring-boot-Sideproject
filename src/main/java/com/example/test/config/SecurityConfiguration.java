package com.example.test.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

    private final UserService userService;
    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    /**
     * 1.이메일, 비밀번호 요청시 등록한 jwt filter가 동작하여 헤더에 토큰 존재 유무 확인
     * 2.최초로 로그인 api진행시 헤더에 토큰이 없는게 당연. 그후 진행
     * 3.Controller에서
     * Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
     *메서드 실행.  UserAuthenticationProvider의 authenticate 메서드실행.
     *
     * 4.authenticate 메서드에서는 이메일과 비밀번호를 받아
     * userService의 loadUserByUsername으로 유저를 DB에서 찾아와 유저정보를 만들고
     *다시 Controller로 돌아와  SecurityContextHolder에 유저정보를 저장
     *
     * 5. 그 정보를 기반으로 JWT 토큰을 생성하고ResponseBody에 토큰을 넣어 리턴
     *
     * 6. 그 이후 다른 API를 요청할때 헤더에 토큰을 넣어 보내면 된다.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        JwtFilter customFilter = new JwtFilter(tokenProvider);
        http.authorizeRequests()
                .antMatchers("/login")
                .permitAll().antMatchers("/signup")
                .permitAll().anyRequest().authenticated()
                .and()
                .formLogin().
                disable()
                .csrf()
                .disable()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)//인증실패401권한없음
                .accessDeniedHandler(jwtAccessDeniedHandler)//인가 실패 403에러
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CustomAuthenticationProvider customAuthenticationProvider() {
        return new CustomAuthenticationProvider(userService);
    }
}
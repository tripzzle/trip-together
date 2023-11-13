package com.tgd.trip.security;

import com.tgd.trip.jwt.JwtAccessDeniedHandler;
import com.tgd.trip.jwt.JwtAuthenticationEntryPoint;
import com.tgd.trip.jwt.JwtAuthenticationFilter;
import com.tgd.trip.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final OAuth2UserService oAuth2UserService;

    private final JwtTokenProvider jwtTokenProvider;
    //      jwtAtuthenticationEntryPoint는 유효한 자격증명을 제공하지 않고 접근하려할 때 401에러를 리턴하는 클래스이다.
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    //     jwtAccessDeniedHandler는 필요한 권한이 존재하지 않을 경우 403 forbidden 에러를 리턴하는 클래스이다.
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;


    // 비밀번호 암호화
    //JWT를 사용하기 위해서는 기본적으로 password encoder가 필요한데, 여기서는 Bycrypt encoder를 사용했다.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // authenticationManager를 Bean 등록
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()//무시한다.
                .antMatchers("/h2-console/**", "/favicon.ico"// `/h2-console/**` 과 `/favicon.ico` 하위 모든 요청과 파비콘은 인증을 무시한다.
                        , "/error", "/static/js/**", "/static/css/**", "/static/img/**", "/static/frontend/**");

    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // CSRF 보안을 사용하지 않습니다.

//                .exceptionHandling()
//                .authenticationEntryPoint(jwtAuthenticationEntryPoint) // JWT 인증 진입 지점 설정
//                .accessDeniedHandler(jwtAccessDeniedHandler) // JWT 접근 거부 핸들러 설정

                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 토큰 기반 인증이므로 세션 사용 안함

                .and()
                .authorizeRequests() // 요청에 대한 사용 권한 체크 시작
                .antMatchers("/api/user/**").authenticated() // "/api/gest/**"로 시작하는 URL은 인증 없이 접근 허용
                .anyRequest().permitAll() // 그 외의 모든 요청은 인증이 필요함

                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class); // JwtAuthenticationFilter를 UsernamePasswordAuthenticationFilter 앞에 추가
        http
                .oauth2Login() // OAuth2 로그인 설정 시작
                .userInfoEndpoint()
                .userService(oAuth2UserService); // OAuth2 사용자 서비스 설정

        return http.build();
    }
}
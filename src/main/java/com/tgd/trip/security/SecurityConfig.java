package com.tgd.trip.security;

import com.tgd.trip.jwt.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final OAuth2UserService oAuth2UserService;

    private final JwtTokenProvider jwtTokenProvider;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    private final OAuth2LoginFailureHandler oAuth2LoginFailureHandler;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()  // 기본 HTTP 인증 방식을 사용하지 않습니다.
                .formLogin().disable()  // 폼 기반 로그인을 사용하지 않습니다.
                .csrf().disable()  // CSRF(Cross-Site Request Forgery) 공격 방지 기능을 사용하지 않습니다.
//                .apply(new CustomFilterConfigurer())  // 사용자 정의 필터를 적용합니다.

//                .and()
                .cors().configurationSource(corsConfigurationSource())  // CORS 구성을 위한 설정 소스를 지정합니다.

                .and()
//                .headers().frameOptions().disable()  // X-Frame-Options를 비활성화합니다.

//                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // 세션을 상태 없이 관리합니다.

                .and()
                .authorizeRequests() // 요청에 대한 사용 권한 체크 시작
                .antMatchers("/api/user/signup").hasRole("GEST")
                .antMatchers("/api/user/**").hasRole("USER")

                .anyRequest().permitAll()
                .and().addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)

                .oauth2Login()
                .successHandler(oAuth2LoginSuccessHandler)  // OAuth2 로그인 성공 시 처리를 담당하는 핸들러를 지정합니다.
                .failureHandler(oAuth2LoginFailureHandler)  // OAuth2 로그인 실패 시 처리를 담당하는 핸들러를 지정합니다.
                .userInfoEndpoint().userService(oAuth2UserService);// OAuth2 로그인 시 사용자 정보를 저장하고 관리하는 서비스를 지정합니다.

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        // CORS 구성을 위한 CorsConfigurationSource를 설정합니다.

        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(List.of("*"));  // 모든 Origin을 허용합니다.
        configuration.setAllowedOriginPatterns(List.of("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PATCH", "PUT", "DELETE", "HEAD"));  // 허용할 HTTP 메서드를 설정합니다.
        configuration.setAllowedHeaders(List.of("*"));  // 모든 헤더를 허용합니다.
        configuration.setExposedHeaders(List.of("*"));  // 노출할 헤더를 설정합니다.
        configuration.setAllowCredentials(true);  // 인증 정보를 포함하지 않도록 설정합니다.
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);  // 모든 경로에 대해 CORS 구성을 적용합니다.

        return source;
    }

    //권한 인증 필터
//    public class CustomFilterConfigurer extends AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity> {
//        // 사용자 정의 필터를 구성하기 위한 클래스입니다.
//
//        @Override
//        public void configure(HttpSecurity httpSecurity) throws Exception {
//            AuthenticationManager authenticationManager = httpSecurity.getSharedObject(AuthenticationManager.class);
//
//            JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager);
//            jwtAuthenticationFilter.setFilterProcessesUrl("/api/auth/login");  // 로그인 API의 URL을 설정합니다.
//            jwtAuthenticationFilter.setAuthenticationSuccessHandler(new MemberAuthenticationSuccessHandler(jwtTokenProvider));  // 인증 성공 시 처리를 담당하는 핸들러를 설정합니다.
//            jwtAuthenticationFilter.setAuthenticationFailureHandler(new MemberAuthenticationFailureHandler());  // 인증 실패 시 처리를 담당하는 핸들러를 설정합니다.
//
//            JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(jwtTokenProvider);  // JWT 토큰 검증 필터를 생성합니다.
//
//            httpSecurity
//                    .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)  // JWT 인증 필터를 UsernamePasswordAuthenticationFilter 앞에 추가합니다.
//                    .addFilterBefore(jwtVerificationFilter, JwtAuthenticationFilter.class)  // JWT 검증 필터를 JwtAuthenticationFilter 앞에 추가합니다.
//                    .addFilterBefore(new JwtExceptionFilter(), jwtVerificationFilter.getClass())  // JWT 예외 처리 필터를 JwtVerificationFilter 앞에 추가합니다.
//                    .exceptionHandling()
//                    .accessDeniedHandler(new JwtAccessDeniedHandler());  // JWT 접근 거부 처리 핸들러를 설정합니다.
//        }
//    }
}
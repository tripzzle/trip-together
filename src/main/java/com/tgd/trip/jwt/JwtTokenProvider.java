package com.tgd.trip.jwt;

import com.tgd.trip.security.SecurityUser;
import com.tgd.trip.user.domain.Role;
import com.tgd.trip.user.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    private String secretKey = "glZuQMjHDtSzqLRadpCkZnmlQBvZEPpIAoLLOchSqPksUXCpYC";

    // 토큰 유효시간 30분
    private long tokenValidTime = 60 * 24 * 60 * 1000L;

    private final UserDetailsService userDetailsService;

    // 객체 초기화, secretKey를 Base64로 인코딩
//    protected void init() {
//        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
//    }

    // JWT 토큰 생성
    public String createToken(Long userPK, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(userPK.toString()); // JWT payload에 저장되는 정보 단위
        claims.put("roles", roles); // 정보 저장 (key-value)
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidTime)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, secretKey) // 사용할 암호화 알고리즘과 signature에 들어갈 secret 값 세팅
                .compact();
    }


    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        System.out.println("여기느옴?");
        SecurityUser userDetails = (SecurityUser) userDetailsService.loadUserByUsername(this.getUserPK(token));
        System.out.println("이게 뭐노" + userDetails);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰에서 회원 정보 추출
    public String getUserPK(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    // Request의 Header에서 token 값을 가져옵니다. "X-AUTH-TOKEN": "TOKEN 값"
    public String resolveToken(HttpServletRequest request) {
        System.out.println("여기는 옴?222222");
        return request.getHeader("X-AUTH-TOKEN");
    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

}

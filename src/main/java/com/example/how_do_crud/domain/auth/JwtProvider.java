package com.example.how_do_crud.domain.auth;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.*;

import static java.lang.System.getenv;

@Component
@Slf4j
public class JwtProvider {
    private final Logger logger = LoggerFactory.getLogger(JwtProvider.class.getName());

    Map<String, String> env = getenv();

    private String secretKey = env.get("KEY"); // 환경변수에서 key 값 가져오기
    private final Long accessTokenValidity; // 1000 당 1초 즉, 30분
    private final Long refreshTokenValidity; // Refresh Token: 7일

    public JwtProvider() {
        accessTokenValidity = 1000L * 60 * 30;
        refreshTokenValidity = 1000L * 60 * 60 * 24 * 7;
    }

    @PostConstruct
    protected void init() {
        // Base64 인코딩
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(Claims claims, Long tokenValidity){
        Date now = new Date();
        Date validity = new Date(now.getTime() + tokenValidity);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    public String createAccessToken(String username, Collection<? extends GrantedAuthority> roles){
        Claims claims = Jwts.claims() // jwt 내부 본문에 들어갈거
                .setSubject(username);
        claims.put("roles", roles);
        return createToken(claims, accessTokenValidity);
    }

    public String createRefreshToken(String username) {
        Claims claims = Jwts.claims()
                .setSubject(username);
        return createToken(claims, refreshTokenValidity);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            logger.warn("토큰 만료됨");
            return false;
        } catch (MalformedJwtException e) {
            logger.warn("토큰 형식 오류");
            return false;
        } catch (UnsupportedJwtException e){
            logger.warn("지원하지 않는 JWT");
            return false;
        } catch (SignatureException e){
            logger.warn("JWT 서명 불일치");
            return false;
        }
    }

    public String getUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}

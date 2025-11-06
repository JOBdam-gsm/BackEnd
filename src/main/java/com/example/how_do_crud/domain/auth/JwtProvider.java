package com.example.how_do_crud.domain.auth;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.*;

import static java.lang.System.getenv;

@Component
public class JwtProvider {
    Map<String, String> env = getenv();

    private String secretKey = env.get("KEY"); // 환경변수에서 key 값 가져오기
    private final Long accessTokenValidity; // 1000 당 1초 즉, 30분

    public JwtProvider() {
        accessTokenValidity = 1000L * 60 * 30;
    }

    @PostConstruct
    protected void init() {
        // Base64 인코딩
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String creatToken(String username, Collection<? extends GrantedAuthority> roles){
        Claims claims = Jwts.claims() // jwt 내부 본문에 들어갈거
                .setSubject(username);
        claims.put("roles", roles);

        Date now = new Date();
        Date validity = new Date(now.getTime() + accessTokenValidity);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
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

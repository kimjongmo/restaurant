package com.example.restaurant.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class JwtUtils {

    private final Key key;

    public JwtUtils(String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    /**
     * 토큰의 payload에 유저의 Id와 name 을 삽입
     * @param userId 유저의 id
     * @param name 유저의 name
     * @param restaurantId
     * @rturn jwt token
     * */
    public String createToken(Long userId, String name, Long restaurantId) {

        JwtBuilder builder = Jwts.builder()
                .claim("userId", userId)
                .claim("name", name);

        if(restaurantId != null)
            builder.claim("restaurantId",restaurantId);

        return builder.signWith(key, SignatureAlgorithm.HS256).compact();
    }

    /**
     * token 으로 부터 payload 를 추출하는 메서드
     * @param token JwtUtils 로부터 만들어진 토큰
     * @return token 의 payload(=claims)
     * */
    public Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)  //JWS = Signature 가 포함된 내용
                .getBody();
    }
}

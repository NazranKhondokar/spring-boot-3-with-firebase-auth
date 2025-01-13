package com.nazran.springboot3firebseauth.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

//import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtGenerator {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @Value("${application.security.jwt.access-token-expiration}")
    private long accessTokenExpire;

    @Value("${application.security.jwt.refresh-token-expiration}")
    private long refreshTokenExpire;

    public String generateToken(Authentication authentication, Long userType, Long expireTime) {
        String username = authentication.getName();
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
//                .signWith(getSigninKey())
                .claim("usertype", userType)
                .compact();
    }

    public String generateToken(Long userType, String username, Long expireTime) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
//                .signWith(getSigninKey())
                .claim("usertype", userType)
                .compact();
    }

    public String generateAccessToken(Authentication authentication, Long userType) {
        return generateToken(authentication, userType, accessTokenExpire);
    }

    public String generateRefreshToken(Authentication authentication, Long userType) {
        return generateToken(authentication, userType, refreshTokenExpire);
    }

    public String generateAccessToken(Long userType, String userName) {
        return generateToken(userType, userName, accessTokenExpire);
    }

    public String generateRefreshToken(Long userType, String userName) {
        return generateToken(userType, userName, refreshTokenExpire);
    }

    public Long accessTokenExpire() {
        return accessTokenExpire;
    }

    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public String getUserTypeFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
        return claims.get("usertype").toString();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            throw new AuthenticationCredentialsNotFoundException("JWT token is not valid " + token);
        }
    }

//    private SecretKey getSigninKey() {
//        byte[] keyBytes = Decoders.BASE64URL.decode(secretKey);
//        return Keys.hmacShaKeyFor(keyBytes);
//    }
}

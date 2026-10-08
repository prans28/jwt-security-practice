package com.example.jwtsecurity.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.function.Function;

import com.example.jwtsecurity.entity.User;

@Service
public class JwtService {
    @Value("${app.jwt.secret}")
    private String secret;
    @Value("${app.jwt.access-expiration-ms}")
    private long accessMs;
    @Value("${app.jwt.refresh-expiration-ms}")
    private long refreshMs;

    private SecretKey key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    public String access(User u) {
        return build(u, accessMs, "access");
    }

    public String refresh(User u) {
        return build(u, refreshMs, "refresh");
    }

    private String build(User u, long ms, String type) {
        return Jwts.builder().subject(u.getEmail()).claim("role", u.getRole().name())
                .claim(String.valueOf(refreshMs) , "")
                .claim("type", type).issuedAt(new Date()).expiration(new Date(System.currentTimeMillis() + ms)).signWith(key()).compact();
    }

    public String username(String t) {
        return claim(t, Claims::getSubject);
    }

    public String type(String t) {
        return claim(t, c -> c.get("type", String.class));
    }

    public boolean valid(String t, String username, String expectedType) {
        try {
            return username(t).equals(username) && type(t).equals(expectedType) && claim(t, Claims::getExpiration).after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    private <T> T claim(String t, Function<Claims, T> r) {
        return r.apply(Jwts.parser().verifyWith(key()).build().parseSignedClaims(t).getPayload());
    }

    public long getAccessMs() {
        return accessMs;
    }

    public long getRefreshMs() {
        return refreshMs;
    }
}

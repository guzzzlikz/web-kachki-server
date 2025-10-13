package org.example.webkachkiserver.components;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

@Component
public class JWTComponent {
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final long endTime = 60 * 60 * 10000; //У секундах
    public String generateToken(String data) {
        return Jwts.builder()
                .setSubject(data)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + endTime))
                .signWith(key)
                .compact();
    }
    public String getDataFromToken(String token) {
        return parseClaims(token).getSubject();
    }
    public boolean validateToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    private Claims parseClaims(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}

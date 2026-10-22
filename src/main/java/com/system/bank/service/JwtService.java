package com.system.bank.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {
    private final SecretKey secretKey;
    public JwtService(@Value("jwt.secrect-key") String secret){
        this.secretKey = Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(secret));
    }

    public String genToken(String username){
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+8400))
                .signWith(secretKey)
                .compact();
    }

}

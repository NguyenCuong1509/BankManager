package com.system.bank.securityConfig;

import com.system.bank.entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@Slf4j
public class JwtService {

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.expiration:86400000}")
    private long jwtExpiration;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(UserEntity user) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("userId", user.getUserId());
        extraClaims.put("role", user.getRole().name());
        if (user.getCustomer() != null) {
            extraClaims.put("customerId", user.getCustomer().getCustomerId());
        }
        return buildToken(extraClaims, user.getUsername(), jwtExpiration);
    }

    private String buildToken(Map<String, Object> extraClaims, String username, long expiration) {
        return Jwts.builder()
                .claims(extraClaims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey())
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claims != null ? claimsResolver.apply(claims) : null;
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            log.error("JWT token đã hết hạn: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("JWT token không hợp lệ: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token không được hỗ trợ: {}", e.getMessage());
        } catch (SecurityException | IllegalArgumentException e) {
            log.error("JWT claims trống hoặc chữ ký không đúng: {}", e.getMessage());
        }
        return null;
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username != null && username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        Date expiration = extractClaim(token, Claims::getExpiration);
        return expiration != null && expiration.before(new Date());
    }
}

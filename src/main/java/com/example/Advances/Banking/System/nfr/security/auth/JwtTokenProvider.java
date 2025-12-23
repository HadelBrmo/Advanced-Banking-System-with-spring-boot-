package com.example.Advances.Banking.System.nfr.security.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * JwtTokenProvider - مزود خدمة JWT tokens
 *
 * المسؤوليات:
 * 1. توليد JWT tokens
 * 2. التحقق من صحة tokens
 * 3. استخراج المعلومات من tokens
 * 4. إدارة صلاحية tokens
 *
 * يستخدم Singleton Pattern ضمنياً (@Service)
 */
@Service
@Slf4j
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * توليد JWT token للمستخدم
     *
     * @param userDetails معلومات المستخدم
     * @return JWT token
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", userDetails.getAuthorities());
        claims.put("timestamp", new Date());

        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * التحقق من صحة token
     *
     * @param token JWT token
     * @param userDetails معلومات المستخدم
     * @return true إذا كان token صالح
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    /**
     * استخراج اسم المستخدم من token
     *
     * @param token JWT token
     * @return اسم المستخدم
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * استخراج تاريخ انتهاء الصلاحية
     *
     * @param token JWT token
     * @return تاريخ الانتهاء
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * استخراج claim محدد من token
     *
     * @param token JWT token
     * @param claimsResolver دالة استخراج claim
     * @return القيمة المستخرجة
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * استخراج جميع claims من token
     *
     * @param token JWT token
     * @return جميع claims
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * التحقق من انتهاء صلاحية token
     *
     * @param token JWT token
     * @return true إذا انتهت صلاحية token
     */
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * الحصول على مفتاح التوقيع
     *
     * @return SecretKey للتوقيع
     */
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    /**
     * توليد token مع claims مخصصة
     *
     * @param claims claims مخصصة
     * @param username اسم المستخدم
     * @return JWT token
     */
    public String generateTokenWithCustomClaims(Map<String, Object> claims, String username) {
        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * استخراج roles من token
     *
     * @param token JWT token
     * @return قائمة الـ roles
     */
    public String extractRoles(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("roles", String.class);
    }
}
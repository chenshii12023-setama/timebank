package com.chengpu.timebank.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT 工具类
 * 用于生成和解析 JWT Token
 * 
 * @author TimeBank Team
 */
@Component
public class JWTUtil {
    
    /**
     * JWT 密钥（从配置文件读取，默认值）
     */
    @Value("${jwt.secret:timebank-secret-key-for-jwt-token-generation-2024}")
    private String secret;
    
    /**
     * JWT 过期时间（毫秒），默认7天
     */
    @Value("${jwt.expiration:604800000}")
    private Long expiration;
    
    /**
     * 获取签名密钥
     * 
     * @return SecretKey 对象
     */
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }
    
    /**
     * 生成 JWT Token
     * 
     * @param username 用户名
     * @param userId 用户ID
     * @return JWT Token 字符串
     */
    public String generateToken(String username, Long userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", username);
        claims.put("userId", userId);
        return generateToken(claims);
    }
    
    /**
     * 生成 JWT Token（内部方法）
     * 
     * @param claims 声明信息
     * @return JWT Token 字符串
     */
    private String generateToken(Map<String, Object> claims) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);
        
        return Jwts.builder()
                .claims(claims)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(getSigningKey())
                .compact();
    }
    
    /**
     * 从 Token 中获取用户名
     * 
     * @param token JWT Token
     * @return 用户名
     */
    public String getUsernameFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.get("username", String.class);
    }
    
    /**
     * 从 Token 中获取用户ID
     * 
     * @param token JWT Token
     * @return 用户ID
     */
    public Long getUserIdFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.get("userId", Long.class);
    }
    
    /**
     * 从 Token 中获取过期时间
     * 
     * @param token JWT Token
     * @return 过期时间
     */
    public Date getExpirationDateFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }
    
    /**
     * 从 Token 中获取所有声明信息
     * 
     * @param token JWT Token
     * @return Claims 对象
     */
    private Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    
    /**
     * 验证 Token 是否有效
     * 
     * @param token JWT Token
     * @return 是否有效
     */
    public Boolean validateToken(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            return !isTokenExpired(claims);
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * 检查 Token 是否过期
     * 
     * @param claims Claims 对象
     * @return 是否过期
     */
    private Boolean isTokenExpired(Claims claims) {
        Date expiration = claims.getExpiration();
        return expiration.before(new Date());
    }
}


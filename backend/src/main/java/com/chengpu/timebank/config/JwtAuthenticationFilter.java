package com.chengpu.timebank.config;

import com.chengpu.timebank.utils.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

/**
 * JWT 认证过滤器
 * 从请求头中提取 JWT Token 并验证，设置用户认证信息
 * 
 * @author TimeBank Team
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    private final JWTUtil jwtUtil;
    
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        
        // 从请求头中获取 Token
        String token = getTokenFromRequest(request);
        
        if (token != null && jwtUtil.validateToken(token)) {
            try {
                // 从 Token 中提取用户信息
                String username = jwtUtil.getUsernameFromToken(token);
                Long userId = jwtUtil.getUserIdFromToken(token);
                
                // 创建认证对象
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                username,
                                null,
                                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
                        );
                
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                
                // 设置到 SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authentication);
                
                // 将用户ID添加到请求头中，方便 Controller 获取
                request.setAttribute("userId", userId);
            } catch (Exception e) {
                // Token 无效，继续过滤链
                logger.error("JWT Token 验证失败", e);
            }
        }
        
        filterChain.doFilter(request, response);
    }
    
    /**
     * 从请求头中提取 Token
     * 支持两种格式：
     * 1. Authorization: Bearer <token>
     * 2. X-Auth-Token: <token>
     */
    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        
        String authToken = request.getHeader("X-Auth-Token");
        if (authToken != null && !authToken.isEmpty()) {
            return authToken;
        }
        
        return null;
    }
}


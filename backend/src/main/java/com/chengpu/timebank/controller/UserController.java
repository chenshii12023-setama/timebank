package com.chengpu.timebank.controller;

import com.chengpu.timebank.service.UserService;
import com.chengpu.timebank.utils.Result;
import com.chengpu.timebank.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 用户控制器
 * 处理用户相关的 API 请求
 * 
 * @author TimeBank Team
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;
    
    /**
     * 获取当前用户信息
     * 
     * @param request HTTP 请求（用于获取用户ID）
     * @return 用户信息
     */
    @GetMapping("/me")
    public Result<UserVO> getCurrentUser(jakarta.servlet.http.HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        try {
            UserVO userVO = userService.getUserById(userId);
            return Result.success(userVO);
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 根据用户ID获取用户信息
     * 
     * @param userId 用户ID
     * @return 用户信息
     */
    @GetMapping("/{userId}")
    public Result<UserVO> getUserById(@PathVariable Long userId) {
        try {
            UserVO userVO = userService.getUserById(userId);
            return Result.success(userVO);
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }
}


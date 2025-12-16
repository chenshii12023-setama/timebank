package com.chengpu.timebank.controller;

import com.chengpu.timebank.dto.LoginDTO;
import com.chengpu.timebank.dto.RegisterDTO;
import com.chengpu.timebank.service.UserService;
import com.chengpu.timebank.utils.Result;
import com.chengpu.timebank.vo.LoginVO;
import com.chengpu.timebank.vo.UserVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 认证控制器
 * 处理用户注册和登录
 * 
 * @author TimeBank Team
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final UserService userService;
    
    /**
     * 用户注册
     * 
     * @param registerDTO 注册信息
     * @return 注册结果
     */
    @PostMapping("/register")
    public Result<UserVO> register(@Valid @RequestBody RegisterDTO registerDTO) {
        try {
            UserVO userVO = userService.register(registerDTO);
            return Result.success("注册成功", userVO);
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 用户登录
     * 
     * @param loginDTO 登录信息
     * @return 登录结果（包含 Token 和用户信息）
     */
    @PostMapping("/login")
    public Result<LoginVO> login(@Valid @RequestBody LoginDTO loginDTO) {
        try {
            LoginVO loginVO = userService.login(loginDTO);
            return Result.success("登录成功", loginVO);
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }
}


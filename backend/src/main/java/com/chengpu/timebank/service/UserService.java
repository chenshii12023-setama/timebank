package com.chengpu.timebank.service;

import com.chengpu.timebank.dto.LoginDTO;
import com.chengpu.timebank.dto.RegisterDTO;
import com.chengpu.timebank.vo.LoginVO;
import com.chengpu.timebank.vo.UserVO;

/**
 * 用户服务接口
 * 
 * @author TimeBank Team
 */
public interface UserService {
    
    /**
     * 用户注册
     * 
     * @param registerDTO 注册信息
     * @return 用户信息
     */
    UserVO register(RegisterDTO registerDTO);
    
    /**
     * 用户登录
     * 
     * @param loginDTO 登录信息
     * @return 登录响应（包含 Token 和用户信息）
     */
    LoginVO login(LoginDTO loginDTO);
    
    /**
     * 根据用户ID获取用户信息
     * 
     * @param userId 用户ID
     * @return 用户信息
     */
    UserVO getUserById(Long userId);
    
    /**
     * 根据用户名获取用户信息
     * 
     * @param username 用户名
     * @return 用户信息
     */
    UserVO getUserByUsername(String username);
}


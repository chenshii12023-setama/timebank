package com.chengpu.timebank.service.impl;

import com.chengpu.timebank.dto.LoginDTO;
import com.chengpu.timebank.dto.RegisterDTO;
import com.chengpu.timebank.entity.User;
import com.chengpu.timebank.repository.UserRepository;
import com.chengpu.timebank.service.UserService;
import com.chengpu.timebank.utils.JWTUtil;
import com.chengpu.timebank.vo.LoginVO;
import com.chengpu.timebank.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户服务实现类
 * 
 * @author TimeBank Team
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;
    
    /**
     * 用户注册
     */
    @Override
    @Transactional
    public UserVO register(RegisterDTO registerDTO) {
        // 检查用户名是否已存在
        if (userRepository.existsByUsername(registerDTO.getUsername())) {
            throw new RuntimeException("用户名已存在");
        }
        
        // 创建新用户
        User user = User.builder()
                .username(registerDTO.getUsername())
                .password(passwordEncoder.encode(registerDTO.getPassword()))
                .nickname(registerDTO.getNickname() != null ? registerDTO.getNickname() : registerDTO.getUsername())
                .phone(registerDTO.getPhone())
                .balance(0)
                .frozenBalance(0)
                .creditScore(100)
                .role("ROLE_USER")
                .build();
        
        // 保存用户
        User savedUser = userRepository.save(user);
        
        // 转换为 VO
        return convertToVO(savedUser);
    }
    
    /**
     * 用户登录
     */
    @Override
    public LoginVO login(LoginDTO loginDTO) {
        // 查找用户
        User user = userRepository.findByUsername(loginDTO.getUsername())
                .orElseThrow(() -> new RuntimeException("用户名或密码错误"));
        
        // 验证密码
        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new RuntimeException("用户名或密码错误");
        }
        
        // 生成 JWT Token
        String token = jwtUtil.generateToken(user.getUsername(), user.getId());
        
        // 转换为 VO
        UserVO userVO = convertToVO(user);
        
        return LoginVO.builder()
                .token(token)
                .user(userVO)
                .build();
    }
    
    /**
     * 根据用户ID获取用户信息
     */
    @Override
    public UserVO getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("用户不存在"));
        return convertToVO(user);
    }
    
    /**
     * 根据用户名获取用户信息
     */
    @Override
    public UserVO getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("用户不存在"));
        return convertToVO(user);
    }
    
    /**
     * 将 User 实体转换为 UserVO
     */
    private UserVO convertToVO(User user) {
        return UserVO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .nickname(user.getNickname())
                .phone(user.getPhone())
                .avatarUrl(user.getAvatarUrl())
                .balance(user.getBalance())
                .frozenBalance(user.getFrozenBalance())
                .creditScore(user.getCreditScore())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .build();
    }
}


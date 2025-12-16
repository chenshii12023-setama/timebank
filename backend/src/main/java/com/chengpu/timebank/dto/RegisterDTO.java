package com.chengpu.timebank.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 用户注册请求 DTO
 * 
 * @author TimeBank Team
 */
@Data
public class RegisterDTO {
    
    /**
     * 用户名（必填，3-20字符）
     */
    @NotBlank(message = "用户名不能为空")
    @Size(min = 3, max = 20, message = "用户名长度必须在3-20字符之间")
    private String username;
    
    /**
     * 密码（必填，6-20字符）
     */
    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 20, message = "密码长度必须在6-20字符之间")
    private String password;
    
    /**
     * 昵称（可选）
     */
    private String nickname;
    
    /**
     * 手机号（可选）
     */
    private String phone;
}


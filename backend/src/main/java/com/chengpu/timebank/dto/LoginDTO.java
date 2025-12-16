package com.chengpu.timebank.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 用户登录请求 DTO
 * 
 * @author TimeBank Team
 */
@Data
public class LoginDTO {
    
    /**
     * 用户名（必填）
     */
    @NotBlank(message = "用户名不能为空")
    private String username;
    
    /**
     * 密码（必填）
     */
    @NotBlank(message = "密码不能为空")
    private String password;
}


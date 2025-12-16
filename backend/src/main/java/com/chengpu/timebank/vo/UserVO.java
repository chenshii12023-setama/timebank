package com.chengpu.timebank.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 用户信息视图对象
 * 
 * @author TimeBank Team
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserVO {
    
    /**
     * 用户ID
     */
    private Long id;
    
    /**
     * 用户名
     */
    private String username;
    
    /**
     * 昵称
     */
    private String nickname;
    
    /**
     * 手机号
     */
    private String phone;
    
    /**
     * 头像URL
     */
    private String avatarUrl;
    
    /**
     * 可用时间币余额
     */
    private Integer balance;
    
    /**
     * 交易冻结资金
     */
    private Integer frozenBalance;
    
    /**
     * 信用分
     */
    private Integer creditScore;
    
    /**
     * 用户角色
     */
    private String role;
    
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
}


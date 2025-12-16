package com.chengpu.timebank.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户实体类
 * 
 * @author TimeBank Team
 */
@Entity
@Table(name = "users", indexes = {
    @Index(name = "idx_username", columnList = "username", unique = true)
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    
    /**
     * 用户主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * 用户名（唯一）
     */
    @Column(nullable = false, unique = true, length = 50)
    private String username;
    
    /**
     * 密码（BCrypt加密存储）
     */
    @Column(nullable = false, length = 255)
    private String password;
    
    /**
     * 可用时间币余额
     */
    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer balance;
    
    /**
     * 交易冻结资金
     */
    @Column(nullable = false, name = "frozen_balance", columnDefinition = "INT DEFAULT 0")
    private Integer frozenBalance;
    
    /**
     * 信用分（默认100）
     */
    @Column(nullable = false, name = "credit_score", columnDefinition = "INT DEFAULT 100")
    private Integer creditScore;
    
    /**
     * 用户角色（ROLE_USER, ROLE_ADMIN）
     */
    @Column(nullable = false, length = 20)
    private String role;
    
    /**
     * 昵称
     */
    @Column(length = 50)
    private String nickname;
    
    /**
     * 手机号
     */
    @Column(length = 20)
    private String phone;
    
    /**
     * 头像URL
     */
    @Column(name = "avatar_url", length = 255)
    private String avatarUrl;
    
    /**
     * 创建时间
     */
    @Column(nullable = false, name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    /**
     * 更新时间
     */
    @Column(nullable = false, name = "updated_at")
    private LocalDateTime updatedAt;
    
    /**
     * 用户发布的任务列表（一对多关系）
     */
    @OneToMany(mappedBy = "publisher", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Task> publishedTasks;
    
    /**
     * 用户接单的任务列表（一对多关系）
     */
    @OneToMany(mappedBy = "worker", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Task> acceptedTasks;
    
    /**
     * 用户的交易流水记录（一对多关系）
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TransactionRecord> transactionRecords;
    
    /**
     * JPA 实体持久化前的回调：设置创建时间和更新时间
     */
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        // 设置默认值
        if (balance == null) {
            balance = 0;
        }
        if (frozenBalance == null) {
            frozenBalance = 0;
        }
        if (creditScore == null) {
            creditScore = 100;
        }
        if (role == null || role.isEmpty()) {
            role = "ROLE_USER";
        }
    }
    
    /**
     * JPA 实体更新前的回调：更新更新时间
     */
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}


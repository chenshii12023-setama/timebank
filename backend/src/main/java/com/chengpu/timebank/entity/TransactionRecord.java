package com.chengpu.timebank.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 交易流水记录实体类
 * 
 * @author TimeBank Team
 */
@Entity
@Table(name = "transaction_records", indexes = {
    @Index(name = "idx_user_id", columnList = "user_id"),
    @Index(name = "idx_task_id", columnList = "task_id"),
    @Index(name = "idx_type", columnList = "type"),
    @Index(name = "idx_created_at", columnList = "created_at")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRecord {
    
    /**
     * 流水记录主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * 变动账户用户ID（外键关联User）
     */
    @Column(nullable = false, name = "user_id")
    private Long userId;
    
    /**
     * 交易对手用户ID（外键关联User，可为空）
     */
    @Column(name = "related_user_id")
    private Long relatedUserId;
    
    /**
     * 交易金额（正数表示收入，负数表示支出）
     */
    @Column(nullable = false)
    private Integer amount;
    
    /**
     * 交易类型（枚举类型）
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TransactionType type;
    
    /**
     * 关联的任务ID（外键关联Task，可为空）
     */
    @Column(name = "task_id")
    private Long taskId;
    
    /**
     * 交易描述/备注
     */
    @Column(length = 500)
    private String description;
    
    /**
     * 交易创建时间
     */
    @Column(nullable = false, name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    /**
     * 变动账户用户（多对一关系）
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;
    
    /**
     * 交易对手用户（多对一关系）
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "related_user_id", insertable = false, updatable = false)
    private User relatedUser;
    
    /**
     * 关联的任务（多对一关系）
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", insertable = false, updatable = false)
    private Task task;
    
    /**
     * JPA 实体持久化前的回调：设置创建时间
     */
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}


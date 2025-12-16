package com.chengpu.timebank.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 任务实体类
 * 
 * @author TimeBank Team
 */
@Entity
@Table(name = "tasks", indexes = {
    @Index(name = "idx_publisher_id", columnList = "publisher_id"),
    @Index(name = "idx_worker_id", columnList = "worker_id"),
    @Index(name = "idx_status", columnList = "status"),
    @Index(name = "idx_created_at", columnList = "created_at")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    
    /**
     * 任务主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * 发布者ID（外键关联User）
     */
    @Column(nullable = false, name = "publisher_id")
    private Long publisherId;
    
    /**
     * 接单者ID（外键关联User，可为空）
     */
    @Column(name = "worker_id")
    private Long workerId;
    
    /**
     * 任务标题
     */
    @Column(nullable = false, length = 200)
    private String title;
    
    /**
     * 任务详情描述
     */
    @Column(columnDefinition = "TEXT")
    private String description;
    
    /**
     * 任务悬赏金额（时间币）
     */
    @Column(nullable = false)
    private Integer price;
    
    /**
     * 任务状态（枚举类型）
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TaskStatus status;
    
    /**
     * 任务截止时间
     */
    @Column(name = "deadline")
    private LocalDateTime deadline;
    
    /**
     * 任务创建时间
     */
    @Column(nullable = false, name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    /**
     * 任务更新时间
     */
    @Column(nullable = false, name = "updated_at")
    private LocalDateTime updatedAt;
    
    /**
     * 任务完成时间
     */
    @Column(name = "completed_at")
    private LocalDateTime completedAt;
    
    /**
     * 发布者（多对一关系）
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_id", insertable = false, updatable = false)
    private User publisher;
    
    /**
     * 接单者（多对一关系）
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worker_id", insertable = false, updatable = false)
    private User worker;
    
    /**
     * 任务相关的交易流水记录（一对多关系）
     */
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TransactionRecord> transactionRecords;
    
    /**
     * JPA 实体持久化前的回调：设置创建时间和更新时间
     */
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (status == null) {
            status = TaskStatus.PENDING;
        }
    }
    
    /**
     * JPA 实体更新前的回调：更新更新时间
     */
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
        // 如果任务状态变为已完成，设置完成时间
        if (status == TaskStatus.COMPLETED && completedAt == null) {
            completedAt = LocalDateTime.now();
        }
    }
}


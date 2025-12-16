package com.chengpu.timebank.vo;

import com.chengpu.timebank.entity.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 任务视图对象
 * 
 * @author TimeBank Team
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskVO {
    
    /**
     * 任务ID
     */
    private Long id;
    
    /**
     * 发布者ID
     */
    private Long publisherId;
    
    /**
     * 发布者用户名
     */
    private String publisherUsername;
    
    /**
     * 接单者ID
     */
    private Long workerId;
    
    /**
     * 接单者用户名
     */
    private String workerUsername;
    
    /**
     * 任务标题
     */
    private String title;
    
    /**
     * 任务详情描述
     */
    private String description;
    
    /**
     * 任务悬赏金额
     */
    private Integer price;
    
    /**
     * 任务状态
     */
    private TaskStatus status;
    
    /**
     * 任务截止时间
     */
    private LocalDateTime deadline;
    
    /**
     * 任务创建时间
     */
    private LocalDateTime createdAt;
    
    /**
     * 任务更新时间
     */
    private LocalDateTime updatedAt;
    
    /**
     * 任务完成时间
     */
    private LocalDateTime completedAt;
}


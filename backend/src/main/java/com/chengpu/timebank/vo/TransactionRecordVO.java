package com.chengpu.timebank.vo;

import com.chengpu.timebank.entity.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 交易流水记录视图对象
 * 
 * @author TimeBank Team
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRecordVO {
    
    /**
     * 流水记录ID
     */
    private Long id;
    
    /**
     * 变动账户用户ID
     */
    private Long userId;
    
    /**
     * 变动账户用户名
     */
    private String username;
    
    /**
     * 交易对手用户ID
     */
    private Long relatedUserId;
    
    /**
     * 交易对手用户名
     */
    private String relatedUsername;
    
    /**
     * 交易金额（正数表示收入，负数表示支出）
     */
    private Integer amount;
    
    /**
     * 交易类型
     */
    private TransactionType type;
    
    /**
     * 关联的任务ID
     */
    private Long taskId;
    
    /**
     * 交易描述/备注
     */
    private String description;
    
    /**
     * 交易创建时间
     */
    private LocalDateTime createdAt;
}


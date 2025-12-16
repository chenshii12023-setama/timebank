package com.chengpu.timebank.service;

import com.chengpu.timebank.entity.TransactionType;
import com.chengpu.timebank.vo.TransactionRecordVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 交易服务接口
 * 
 * @author TimeBank Team
 */
public interface TransactionService {
    
    /**
     * 记录交易流水
     * 
     * @param userId 变动账户用户ID
     * @param relatedUserId 交易对手用户ID（可为空）
     * @param amount 交易金额（正数表示收入，负数表示支出）
     * @param type 交易类型
     * @param taskId 关联的任务ID（可为空）
     * @param description 交易描述
     * @return 交易流水记录
     */
    TransactionRecordVO recordTransaction(
            Long userId,
            Long relatedUserId,
            Integer amount,
            TransactionType type,
            Long taskId,
            String description
    );
    
    /**
     * 根据用户ID查询交易流水（分页）
     * 
     * @param userId 用户ID
     * @param pageable 分页参数
     * @return 交易流水分页结果
     */
    Page<TransactionRecordVO> getTransactionsByUserId(Long userId, Pageable pageable);
    
    /**
     * 根据用户ID和交易类型查询交易流水（分页）
     * 
     * @param userId 用户ID
     * @param type 交易类型
     * @param pageable 分页参数
     * @return 交易流水分页结果
     */
    Page<TransactionRecordVO> getTransactionsByUserIdAndType(Long userId, TransactionType type, Pageable pageable);
}


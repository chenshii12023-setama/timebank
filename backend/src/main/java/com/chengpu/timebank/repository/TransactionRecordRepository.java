package com.chengpu.timebank.repository;

import com.chengpu.timebank.entity.TransactionRecord;
import com.chengpu.timebank.entity.TransactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 交易流水记录数据访问层
 * 
 * @author TimeBank Team
 */
@Repository
public interface TransactionRecordRepository extends JpaRepository<TransactionRecord, Long> {
    
    /**
     * 根据用户ID查询交易流水（分页）
     * 
     * @param userId 用户ID
     * @param pageable 分页参数
     * @return 交易流水分页结果
     */
    Page<TransactionRecord> findByUserId(Long userId, Pageable pageable);
    
    /**
     * 根据用户ID和交易类型查询交易流水（分页）
     * 
     * @param userId 用户ID
     * @param type 交易类型
     * @param pageable 分页参数
     * @return 交易流水分页结果
     */
    Page<TransactionRecord> findByUserIdAndType(Long userId, TransactionType type, Pageable pageable);
    
    /**
     * 根据任务ID查询交易流水
     * 
     * @param taskId 任务ID
     * @return 交易流水列表
     */
    List<TransactionRecord> findByTaskId(Long taskId);
}


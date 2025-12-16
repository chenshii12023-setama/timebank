package com.chengpu.timebank.service.impl;

import com.chengpu.timebank.entity.TransactionRecord;
import com.chengpu.timebank.entity.TransactionType;
import com.chengpu.timebank.entity.User;
import com.chengpu.timebank.repository.TransactionRecordRepository;
import com.chengpu.timebank.repository.UserRepository;
import com.chengpu.timebank.service.TransactionService;
import com.chengpu.timebank.vo.TransactionRecordVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 交易服务实现类
 * 
 * @author TimeBank Team
 */
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    
    private final TransactionRecordRepository transactionRecordRepository;
    private final UserRepository userRepository;
    
    /**
     * 记录交易流水
     */
    @Override
    @Transactional
    public TransactionRecordVO recordTransaction(
            Long userId,
            Long relatedUserId,
            Integer amount,
            TransactionType type,
            Long taskId,
            String description
    ) {
        // 创建交易流水记录
        TransactionRecord record = TransactionRecord.builder()
                .userId(userId)
                .relatedUserId(relatedUserId)
                .amount(amount)
                .type(type)
                .taskId(taskId)
                .description(description)
                .build();
        
        TransactionRecord savedRecord = transactionRecordRepository.save(record);
        
        // 转换为 VO
        User user = userRepository.findById(userId).orElse(null);
        User relatedUser = relatedUserId != null ? userRepository.findById(relatedUserId).orElse(null) : null;
        
        return convertToVO(savedRecord, user, relatedUser);
    }
    
    /**
     * 根据用户ID查询交易流水（分页）
     */
    @Override
    public Page<TransactionRecordVO> getTransactionsByUserId(Long userId, Pageable pageable) {
        Page<TransactionRecord> records = transactionRecordRepository.findByUserId(userId, pageable);
        
        return records.map(record -> {
            User user = userRepository.findById(record.getUserId()).orElse(null);
            User relatedUser = record.getRelatedUserId() != null 
                    ? userRepository.findById(record.getRelatedUserId()).orElse(null) 
                    : null;
            return convertToVO(record, user, relatedUser);
        });
    }
    
    /**
     * 根据用户ID和交易类型查询交易流水（分页）
     */
    @Override
    public Page<TransactionRecordVO> getTransactionsByUserIdAndType(Long userId, TransactionType type, Pageable pageable) {
        Page<TransactionRecord> records = transactionRecordRepository.findByUserIdAndType(userId, type, pageable);
        
        return records.map(record -> {
            User user = userRepository.findById(record.getUserId()).orElse(null);
            User relatedUser = record.getRelatedUserId() != null 
                    ? userRepository.findById(record.getRelatedUserId()).orElse(null) 
                    : null;
            return convertToVO(record, user, relatedUser);
        });
    }
    
    /**
     * 将 TransactionRecord 实体转换为 TransactionRecordVO
     */
    private TransactionRecordVO convertToVO(TransactionRecord record, User user, User relatedUser) {
        return TransactionRecordVO.builder()
                .id(record.getId())
                .userId(record.getUserId())
                .username(user != null ? user.getUsername() : null)
                .relatedUserId(record.getRelatedUserId())
                .relatedUsername(relatedUser != null ? relatedUser.getUsername() : null)
                .amount(record.getAmount())
                .type(record.getType())
                .taskId(record.getTaskId())
                .description(record.getDescription())
                .createdAt(record.getCreatedAt())
                .build();
    }
}


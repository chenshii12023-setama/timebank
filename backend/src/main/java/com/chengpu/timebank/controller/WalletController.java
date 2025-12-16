package com.chengpu.timebank.controller;

import com.chengpu.timebank.entity.TransactionType;
import com.chengpu.timebank.service.TransactionService;
import com.chengpu.timebank.utils.Result;
import com.chengpu.timebank.vo.TransactionRecordVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

/**
 * 钱包控制器
 * 处理资产和交易流水相关的 API 请求
 * 
 * @author TimeBank Team
 */
@RestController
@RequestMapping("/wallet")
@RequiredArgsConstructor
public class WalletController {
    
    private final TransactionService transactionService;
    
    /**
     * 查询交易流水（分页）
     * 
     * @param request HTTP 请求（用于获取用户ID）
     * @param type 交易类型（可选）
     * @param page 页码（从0开始，默认0）
     * @param size 每页大小（默认10）
     * @param sortBy 排序字段（默认createdAt）
     * @param sortDir 排序方向（ASC/DESC，默认DESC）
     * @return 交易流水分页结果
     */
    @GetMapping("/transactions")
    public Result<Page<TransactionRecordVO>> getTransactions(
            jakarta.servlet.http.HttpServletRequest request,
            @RequestParam(required = false) TransactionType type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDir) {
        try {
            Long userId = (Long) request.getAttribute("userId");
            Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);
            Pageable pageable = PageRequest.of(page, size, sort);
            
            Page<TransactionRecordVO> transactions;
            if (type != null) {
                transactions = transactionService.getTransactionsByUserIdAndType(userId, type, pageable);
            } else {
                transactions = transactionService.getTransactionsByUserId(userId, pageable);
            }
            
            return Result.success(transactions);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}


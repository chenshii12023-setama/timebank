package com.chengpu.timebank.entity;

/**
 * 交易流水类型枚举
 * 
 * @author TimeBank Team
 */
public enum TransactionType {
    /**
     * 发布锁单：发布任务时冻结资金
     */
    PUBLISH_LOCK,
    
    /**
     * 任务收入：完成任务后获得收入
     */
    TASK_INCOME,
    
    /**
     * 退款：任务取消或退款时的资金退回
     */
    REFUND,
    
    /**
     * 充值：用户充值时间币
     */
    RECHARGE,
    
    /**
     * 提现：用户提现时间币
     */
    WITHDRAW
}


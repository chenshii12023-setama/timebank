package com.chengpu.timebank.entity;

/**
 * 任务状态枚举
 * 
 * @author TimeBank Team
 */
public enum TaskStatus {
    /**
     * 待接单：任务已发布，等待接单
     */
    PENDING,
    
    /**
     * 已接单：任务已被接单，但尚未开始执行
     */
    ACCEPTED,
    
    /**
     * 进行中：任务正在执行中
     */
    IN_PROGRESS,
    
    /**
     * 已提交：服务方已完成任务并提交
     */
    SUBMITTED,
    
    /**
     * 已完成：需求方已确认验收，任务完成
     */
    COMPLETED,
    
    /**
     * 已取消：任务被取消
     */
    CANCELLED
}


package com.chengpu.timebank.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 创建任务请求 DTO
 * 
 * @author TimeBank Team
 */
@Data
public class CreateTaskDTO {
    
    /**
     * 任务标题（必填，最大200字符）
     */
    @NotBlank(message = "任务标题不能为空")
    @Size(max = 200, message = "任务标题不能超过200字符")
    private String title;
    
    /**
     * 任务详情描述（可选）
     */
    private String description;
    
    /**
     * 任务悬赏金额（必填，最小值为1）
     */
    @NotNull(message = "悬赏金额不能为空")
    @Min(value = 1, message = "悬赏金额必须大于0")
    private Integer price;
    
    /**
     * 任务截止时间（可选）
     */
    private LocalDateTime deadline;
}


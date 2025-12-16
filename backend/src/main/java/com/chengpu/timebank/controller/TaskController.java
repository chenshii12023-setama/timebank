package com.chengpu.timebank.controller;

import com.chengpu.timebank.dto.CreateTaskDTO;
import com.chengpu.timebank.entity.TaskStatus;
import com.chengpu.timebank.service.TaskService;
import com.chengpu.timebank.utils.Result;
import com.chengpu.timebank.vo.TaskVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

/**
 * 任务控制器
 * 处理任务相关的 API 请求
 * 
 * @author TimeBank Team
 */
@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {
    
    private final TaskService taskService;
    
    /**
     * 发布任务
     * 
     * @param request HTTP 请求（用于获取用户ID）
     * @param createTaskDTO 任务信息
     * @return 创建的任务信息
     */
    @PostMapping
    public Result<TaskVO> createTask(
            jakarta.servlet.http.HttpServletRequest request,
            @Valid @RequestBody CreateTaskDTO createTaskDTO) {
        Long publisherId = (Long) request.getAttribute("userId");
        try {
            TaskVO taskVO = taskService.createTask(publisherId, createTaskDTO);
            return Result.success("任务发布成功", taskVO);
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 获取任务详情
     * 
     * @param taskId 任务ID
     * @return 任务信息
     */
    @GetMapping("/{taskId}")
    public Result<TaskVO> getTaskById(@PathVariable Long taskId) {
        try {
            TaskVO taskVO = taskService.getTaskById(taskId);
            return Result.success(taskVO);
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 分页查询任务列表（支持多条件筛选）
     * 
     * @param status 任务状态（可选）
     * @param minPrice 最低金额（可选）
     * @param maxPrice 最高金额（可选）
     * @param title 标题关键字（可选）
     * @param page 页码（从0开始，默认0）
     * @param size 每页大小（默认10）
     * @param sortBy 排序字段（默认createdAt）
     * @param sortDir 排序方向（ASC/DESC，默认DESC）
     * @return 任务分页结果
     */
    @GetMapping
    public Result<Page<TaskVO>> getTasks(
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice,
            @RequestParam(required = false) String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDir) {
        try {
            Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);
            Pageable pageable = PageRequest.of(page, size, sort);
            
            Page<TaskVO> tasks = taskService.getTasks(status, minPrice, maxPrice, title, pageable);
            return Result.success(tasks);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 接单
     * 
     * @param taskId 任务ID
     * @param request HTTP 请求（用于获取用户ID）
     * @return 任务信息
     */
    @PostMapping("/{taskId}/accept")
    public Result<TaskVO> acceptTask(
            @PathVariable Long taskId,
            jakarta.servlet.http.HttpServletRequest request) {
        Long workerId = (Long) request.getAttribute("userId");
        try {
            TaskVO taskVO = taskService.acceptTask(taskId, workerId);
            return Result.success("接单成功", taskVO);
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 服务方提交任务完成
     * 
     * @param taskId 任务ID
     * @param request HTTP 请求（用于获取用户ID）
     * @return 任务信息
     */
    @PostMapping("/{taskId}/submit")
    public Result<TaskVO> submitTask(
            @PathVariable Long taskId,
            jakarta.servlet.http.HttpServletRequest request) {
        Long workerId = (Long) request.getAttribute("userId");
        try {
            TaskVO taskVO = taskService.submitTask(taskId, workerId);
            return Result.success("任务提交成功", taskVO);
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 需求方确认验收（触发资金结算）
     * 
     * @param taskId 任务ID
     * @param request HTTP 请求（用于获取用户ID）
     * @return 任务信息
     */
    @PostMapping("/{taskId}/confirm")
    public Result<TaskVO> confirmTask(
            @PathVariable Long taskId,
            jakarta.servlet.http.HttpServletRequest request) {
        Long publisherId = (Long) request.getAttribute("userId");
        try {
            TaskVO taskVO = taskService.confirmTask(taskId, publisherId);
            return Result.success("任务验收成功，资金已结算", taskVO);
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 取消任务
     * 
     * @param taskId 任务ID
     * @param request HTTP 请求（用于获取用户ID）
     * @return 任务信息
     */
    @PostMapping("/{taskId}/cancel")
    public Result<TaskVO> cancelTask(
            @PathVariable Long taskId,
            jakarta.servlet.http.HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        try {
            TaskVO taskVO = taskService.cancelTask(taskId, userId);
            return Result.success("任务已取消", taskVO);
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }
}


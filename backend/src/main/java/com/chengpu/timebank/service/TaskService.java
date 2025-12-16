package com.chengpu.timebank.service;

import com.chengpu.timebank.dto.CreateTaskDTO;
import com.chengpu.timebank.entity.TaskStatus;
import com.chengpu.timebank.vo.TaskVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 任务服务接口
 * 
 * @author TimeBank Team
 */
public interface TaskService {
    
    /**
     * 发布任务（核心逻辑：校验余额充足，开启事务扣除可用余额至冻结余额）
     * 
     * @param publisherId 发布者ID
     * @param createTaskDTO 任务信息
     * @return 创建的任务信息
     */
    TaskVO createTask(Long publisherId, CreateTaskDTO createTaskDTO);
    
    /**
     * 根据ID获取任务详情
     * 
     * @param taskId 任务ID
     * @return 任务信息
     */
    TaskVO getTaskById(Long taskId);
    
    /**
     * 分页查询任务列表（支持多条件筛选）
     * 
     * @param status 任务状态（可为空）
     * @param minPrice 最低金额（可为空）
     * @param maxPrice 最高金额（可为空）
     * @param title 标题关键字（可为空）
     * @param pageable 分页参数
     * @return 任务分页结果
     */
    Page<TaskVO> getTasks(TaskStatus status, Integer minPrice, Integer maxPrice, String title, Pageable pageable);
    
    /**
     * 接单（乐观锁控制，防止多人抢单）
     * 
     * @param taskId 任务ID
     * @param workerId 接单者ID
     * @return 任务信息
     */
    TaskVO acceptTask(Long taskId, Long workerId);
    
    /**
     * 服务方提交任务完成
     * 
     * @param taskId 任务ID
     * @param workerId 服务方ID（用于权限校验）
     * @return 任务信息
     */
    TaskVO submitTask(Long taskId, Long workerId);
    
    /**
     * 需求方确认验收（触发资金结算）
     * 
     * @param taskId 任务ID
     * @param publisherId 发布者ID（用于权限校验）
     * @return 任务信息
     */
    TaskVO confirmTask(Long taskId, Long publisherId);
    
    /**
     * 取消任务（根据状态决定是否解冻资金）
     * 
     * @param taskId 任务ID
     * @param userId 操作者ID（用于权限校验）
     * @return 任务信息
     */
    TaskVO cancelTask(Long taskId, Long userId);
}


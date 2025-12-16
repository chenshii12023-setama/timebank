package com.chengpu.timebank.service.impl;

import com.chengpu.timebank.dto.CreateTaskDTO;
import com.chengpu.timebank.entity.Task;
import com.chengpu.timebank.entity.TaskStatus;
import com.chengpu.timebank.entity.TransactionRecord;
import com.chengpu.timebank.entity.TransactionType;
import com.chengpu.timebank.entity.User;
import com.chengpu.timebank.repository.TaskRepository;
import com.chengpu.timebank.repository.TransactionRecordRepository;
import com.chengpu.timebank.repository.UserRepository;
import com.chengpu.timebank.service.TaskService;
import com.chengpu.timebank.service.TransactionService;
import com.chengpu.timebank.vo.TaskVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 任务服务实现类
 * 
 * @author TimeBank Team
 */
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TransactionRecordRepository transactionRecordRepository;
    private final TransactionService transactionService;
    
    /**
     * 发布任务（核心逻辑：校验余额充足，开启事务扣除可用余额至冻结余额）
     */
    @Override
    @Transactional
    public TaskVO createTask(Long publisherId, CreateTaskDTO createTaskDTO) {
        // 获取发布者信息
        User publisher = userRepository.findById(publisherId)
                .orElseThrow(() -> new RuntimeException("用户不存在"));
        
        // 校验余额充足
        if (publisher.getBalance() < createTaskDTO.getPrice()) {
            throw new RuntimeException("余额不足，无法发布任务");
        }
        
        // 校验信用分（信用分 < 60 禁止发布任务）
        if (publisher.getCreditScore() < 60) {
            throw new RuntimeException("信用分过低，无法发布任务");
        }
        
        // 扣除可用余额至冻结余额
        publisher.setBalance(publisher.getBalance() - createTaskDTO.getPrice());
        publisher.setFrozenBalance(publisher.getFrozenBalance() + createTaskDTO.getPrice());
        userRepository.save(publisher);
        
        // 创建任务
        Task task = Task.builder()
                .publisherId(publisherId)
                .title(createTaskDTO.getTitle())
                .description(createTaskDTO.getDescription())
                .price(createTaskDTO.getPrice())
                .status(TaskStatus.PENDING)
                .deadline(createTaskDTO.getDeadline())
                .build();
        
        Task savedTask = taskRepository.save(task);
        
        // 记录交易流水（发布锁单）
        transactionService.recordTransaction(
                publisherId,
                null,
                -createTaskDTO.getPrice(),
                TransactionType.PUBLISH_LOCK,
                savedTask.getId(),
                "发布任务：" + createTaskDTO.getTitle()
        );
        
        return convertToVO(savedTask, publisher, null);
    }
    
    /**
     * 根据ID获取任务详情
     */
    @Override
    public TaskVO getTaskById(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("任务不存在"));
        
        User publisher = task.getPublisherId() != null 
                ? userRepository.findById(task.getPublisherId()).orElse(null) 
                : null;
        User worker = task.getWorkerId() != null 
                ? userRepository.findById(task.getWorkerId()).orElse(null) 
                : null;
        
        return convertToVO(task, publisher, worker);
    }
    
    /**
     * 分页查询任务列表（支持多条件筛选）
     */
    @Override
    public Page<TaskVO> getTasks(TaskStatus status, Integer minPrice, Integer maxPrice, String title, Pageable pageable) {
        Page<Task> tasks = taskRepository.findByConditions(status, minPrice, maxPrice, title, pageable);
        
        return tasks.map(task -> {
            User publisher = task.getPublisherId() != null 
                    ? userRepository.findById(task.getPublisherId()).orElse(null) 
                    : null;
            User worker = task.getWorkerId() != null 
                    ? userRepository.findById(task.getWorkerId()).orElse(null) 
                    : null;
            return convertToVO(task, publisher, worker);
        });
    }
    
    /**
     * 接单（乐观锁控制，防止多人抢单）
     */
    @Override
    @Transactional
    public TaskVO acceptTask(Long taskId, Long workerId) {
        // 使用乐观锁查询任务
        Task task = taskRepository.findByIdWithLock(taskId)
                .orElseThrow(() -> new RuntimeException("任务不存在"));
        
        // 校验任务状态
        if (task.getStatus() != TaskStatus.PENDING) {
            throw new RuntimeException("任务状态不允许接单");
        }
        
        // 校验接单人不是发布人
        if (task.getPublisherId().equals(workerId)) {
            throw new RuntimeException("不能接自己的任务");
        }
        
        // 获取接单者信息
        User worker = userRepository.findById(workerId)
                .orElseThrow(() -> new RuntimeException("用户不存在"));
        
        // 校验信用分（信用分 < 60 禁止接单）
        if (worker.getCreditScore() < 60) {
            throw new RuntimeException("信用分过低，无法接单");
        }
        
        // 更新任务状态和接单者
        task.setWorkerId(workerId);
        task.setStatus(TaskStatus.ACCEPTED);
        Task savedTask = taskRepository.save(task);
        
        return convertToVO(savedTask, 
                userRepository.findById(task.getPublisherId()).orElse(null), 
                worker);
    }
    
    /**
     * 服务方提交任务完成
     */
    @Override
    @Transactional
    public TaskVO submitTask(Long taskId, Long workerId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("任务不存在"));
        
        // 权限校验：必须是接单者
        if (!task.getWorkerId().equals(workerId)) {
            throw new RuntimeException("无权操作此任务");
        }
        
        // 校验任务状态
        if (task.getStatus() != TaskStatus.ACCEPTED && task.getStatus() != TaskStatus.IN_PROGRESS) {
            throw new RuntimeException("任务状态不允许提交");
        }
        
        // 更新任务状态
        task.setStatus(TaskStatus.SUBMITTED);
        Task savedTask = taskRepository.save(task);
        
        User publisher = userRepository.findById(task.getPublisherId()).orElse(null);
        User worker = userRepository.findById(workerId).orElse(null);
        
        return convertToVO(savedTask, publisher, worker);
    }
    
    /**
     * 需求方确认验收（触发资金结算）
     */
    @Override
    @Transactional
    public TaskVO confirmTask(Long taskId, Long publisherId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("任务不存在"));
        
        // 权限校验：必须是发布者
        if (!task.getPublisherId().equals(publisherId)) {
            throw new RuntimeException("无权操作此任务");
        }
        
        // 校验任务状态
        if (task.getStatus() != TaskStatus.SUBMITTED) {
            throw new RuntimeException("任务状态不允许确认验收");
        }
        
        // 获取发布者和接单者信息
        User publisher = userRepository.findById(publisherId)
                .orElseThrow(() -> new RuntimeException("发布者不存在"));
        User worker = userRepository.findById(task.getWorkerId())
                .orElseThrow(() -> new RuntimeException("接单者不存在"));
        
        // 资金结算：Publisher.frozen -= price, Worker.balance += price
        publisher.setFrozenBalance(publisher.getFrozenBalance() - task.getPrice());
        worker.setBalance(worker.getBalance() + task.getPrice());
        
        userRepository.save(publisher);
        userRepository.save(worker);
        
        // 记录交易流水（任务收入）
        transactionService.recordTransaction(
                task.getWorkerId(),
                publisherId,
                task.getPrice(),
                TransactionType.TASK_INCOME,
                taskId,
                "完成任务：" + task.getTitle()
        );
        
        // 更新任务状态
        task.setStatus(TaskStatus.COMPLETED);
        Task savedTask = taskRepository.save(task);
        
        return convertToVO(savedTask, publisher, worker);
    }
    
    /**
     * 取消任务（根据状态决定是否解冻资金）
     */
    @Override
    @Transactional
    public TaskVO cancelTask(Long taskId, Long userId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("任务不存在"));
        
        // 权限校验：必须是发布者或接单者
        if (!task.getPublisherId().equals(userId) && 
            (task.getWorkerId() == null || !task.getWorkerId().equals(userId))) {
            throw new RuntimeException("无权操作此任务");
        }
        
        // 校验任务状态（已完成的任务不能取消）
        if (task.getStatus() == TaskStatus.COMPLETED) {
            throw new RuntimeException("已完成的任务不能取消");
        }
        
        User publisher = userRepository.findById(task.getPublisherId())
                .orElseThrow(() -> new RuntimeException("发布者不存在"));
        
        // 如果任务还未被接单，解冻资金
        if (task.getStatus() == TaskStatus.PENDING) {
            publisher.setBalance(publisher.getBalance() + task.getPrice());
            publisher.setFrozenBalance(publisher.getFrozenBalance() - task.getPrice());
            userRepository.save(publisher);
            
            // 记录交易流水（退款）
            transactionService.recordTransaction(
                    task.getPublisherId(),
                    null,
                    task.getPrice(),
                    TransactionType.REFUND,
                    taskId,
                    "取消任务：" + task.getTitle()
            );
        }
        
        // 更新任务状态
        task.setStatus(TaskStatus.CANCELLED);
        Task savedTask = taskRepository.save(task);
        
        User worker = task.getWorkerId() != null 
                ? userRepository.findById(task.getWorkerId()).orElse(null) 
                : null;
        
        return convertToVO(savedTask, publisher, worker);
    }
    
    /**
     * 将 Task 实体转换为 TaskVO
     */
    private TaskVO convertToVO(Task task, User publisher, User worker) {
        return TaskVO.builder()
                .id(task.getId())
                .publisherId(task.getPublisherId())
                .publisherUsername(publisher != null ? publisher.getUsername() : null)
                .workerId(task.getWorkerId())
                .workerUsername(worker != null ? worker.getUsername() : null)
                .title(task.getTitle())
                .description(task.getDescription())
                .price(task.getPrice())
                .status(task.getStatus())
                .deadline(task.getDeadline())
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())
                .completedAt(task.getCompletedAt())
                .build();
    }
}


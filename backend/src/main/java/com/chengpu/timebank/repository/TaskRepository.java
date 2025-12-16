package com.chengpu.timebank.repository;

import com.chengpu.timebank.entity.Task;
import com.chengpu.timebank.entity.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

/**
 * 任务数据访问层
 * 
 * @author TimeBank Team
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    
    /**
     * 根据状态分页查询任务
     * 
     * @param status 任务状态
     * @param pageable 分页参数
     * @return 任务分页结果
     */
    Page<Task> findByStatus(TaskStatus status, Pageable pageable);
    
    /**
     * 根据发布者ID查询任务列表
     * 
     * @param publisherId 发布者ID
     * @param pageable 分页参数
     * @return 任务分页结果
     */
    Page<Task> findByPublisherId(Long publisherId, Pageable pageable);
    
    /**
     * 根据接单者ID查询任务列表
     * 
     * @param workerId 接单者ID
     * @param pageable 分页参数
     * @return 任务分页结果
     */
    Page<Task> findByWorkerId(Long workerId, Pageable pageable);
    
    /**
     * 根据标题模糊查询任务（分页）
     * 
     * @param title 标题关键字
     * @param pageable 分页参数
     * @return 任务分页结果
     */
    Page<Task> findByTitleContaining(String title, Pageable pageable);
    
    /**
     * 多条件查询任务（分页）
     * 支持按状态、金额范围、标题关键字查询
     * 
     * @param status 任务状态（可为空）
     * @param minPrice 最低金额（可为空）
     * @param maxPrice 最高金额（可为空）
     * @param title 标题关键字（可为空）
     * @param pageable 分页参数
     * @return 任务分页结果
     */
    @Query("SELECT t FROM Task t WHERE " +
           "(:status IS NULL OR t.status = :status) AND " +
           "(:minPrice IS NULL OR t.price >= :minPrice) AND " +
           "(:maxPrice IS NULL OR t.price <= :maxPrice) AND " +
           "(:title IS NULL OR t.title LIKE %:title%)")
    Page<Task> findByConditions(
            @Param("status") TaskStatus status,
            @Param("minPrice") Integer minPrice,
            @Param("maxPrice") Integer maxPrice,
            @Param("title") String title,
            Pageable pageable
    );
    
    /**
     * 使用乐观锁查询任务（用于接单时的并发控制）
     * 
     * @param id 任务ID
     * @return 任务对象
     */
    @Lock(LockModeType.OPTIMISTIC)
    @Query("SELECT t FROM Task t WHERE t.id = :id")
    Optional<Task> findByIdWithLock(@Param("id") Long id);
}


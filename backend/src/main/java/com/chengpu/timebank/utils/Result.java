package com.chengpu.timebank.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一返回结果封装类
 * 
 * @param <T> 返回数据的类型
 * @author TimeBank Team
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    
    /**
     * 业务状态码
     * 200: 成功
     * 其他: 失败（具体错误码）
     */
    private Integer code;
    
    /**
     * 提示信息
     */
    private String msg;
    
    /**
     * 业务数据
     */
    private T data;
    
    /**
     * 成功返回（无数据）
     * 
     * @return Result 对象
     */
    public static <T> Result<T> success() {
        return new Result<>(200, "success", null);
    }
    
    /**
     * 成功返回（带数据）
     * 
     * @param data 返回的数据
     * @return Result 对象
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(200, "success", data);
    }
    
    /**
     * 成功返回（自定义消息）
     * 
     * @param msg 提示信息
     * @param data 返回的数据
     * @return Result 对象
     */
    public static <T> Result<T> success(String msg, T data) {
        return new Result<>(200, msg, data);
    }
    
    /**
     * 失败返回（默认错误码400）
     * 
     * @param msg 错误信息
     * @return Result 对象
     */
    public static <T> Result<T> error(String msg) {
        return new Result<>(400, msg, null);
    }
    
    /**
     * 失败返回（自定义错误码）
     * 
     * @param code 错误码
     * @param msg 错误信息
     * @return Result 对象
     */
    public static <T> Result<T> error(Integer code, String msg) {
        return new Result<>(code, msg, null);
    }
    
    /**
     * 未授权返回（401）
     * 
     * @param msg 错误信息
     * @return Result 对象
     */
    public static <T> Result<T> unauthorized(String msg) {
        return new Result<>(401, msg, null);
    }
    
    /**
     * 禁止访问返回（403）
     * 
     * @param msg 错误信息
     * @return Result 对象
     */
    public static <T> Result<T> forbidden(String msg) {
        return new Result<>(403, msg, null);
    }
    
    /**
     * 资源未找到返回（404）
     * 
     * @param msg 错误信息
     * @return Result 对象
     */
    public static <T> Result<T> notFound(String msg) {
        return new Result<>(404, msg, null);
    }
}


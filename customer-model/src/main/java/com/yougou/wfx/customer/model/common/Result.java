package com.yougou.wfx.customer.model.common;

import com.yougou.wfx.dto.base.WFXResult;

import java.io.Serializable;

/**
 * @author zhang.sj, Email:zhang.sj@yougou.com
 * @version 1.0 on 2016/3/30
 */
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 3998190647123277736L;
    /**
     * 返回标识
     */
    private boolean success;
    /** 状态码 200:正常;500:服务器错误;501:未完全执行成功 */
    private int code = 200;
    /**
     * 数据
     */
    private T data;
    /**
     * 返回消息
     */
    private String message;

    private Result(boolean success, int code, T data, String message) {
        this.success = success;
        this.code = code;
        this.data = data;
        this.message = message;
    }

    private Result(boolean success, T data, String message) {
        this.success = success;
        this.data = data;
        this.message = message;
        if (! success) {
            code = 500;
        }
    }


    public int getCode() {
        return code;
    }

    public Result setCode(int code) {
        this.code = code;
        return this;
    }

    public static <T> Result<T> create() {
        return new Result<>(Boolean.TRUE, null, "");
    }

    /**
     * 创建结果集
     *
     * @param success
     *         执行结果
     * @param message
     *         提示消息
     *
     * @since 1.0 Created by lipangeng on 16/3/30 下午3:21. Email:lipg@outlook.com.
     */
    public static <T> Result<T> create(boolean success, String message) {
        return new Result<>(success, null, message);
    }

    /**
     * 创建结果集
     *
     * @param success
     *         执行结果
     * @param code
     *         状态码
     * @param message
     *         提示消息
     *
     * @since 1.0 Created by lipangeng on 16/3/30 下午3:21. Email:lipg@outlook.com.
     */
    public static <T> Result<T> create(boolean success, int code, String message) {
        return new Result<>(success, code, null, message);
    }

    /**
     * 创建结果集
     *
     * @param success
     *         执行结果
     * @param code
     *         状态码
     * @param message
     *         提示消息
     * @param data
     *         执行结果返回类型
     *
     * @since 1.0 Created by lipangeng on 16/3/30 下午3:21. Email:lipg@outlook.com.
     */
    public static <T> Result<T> create(boolean success, int code, String message, T data) {
        return new Result<>(success, code, data, message);
    }

    /**
     * 创建结果集
     *
     * @param success
     *         执行结果
     * @param data
     *         结果返回的任意类型
     * @param message
     *         提示消息
     *
     * @since 1.0 Created by lipangeng on 16/3/30 下午3:21. Email:lipg@outlook.com.
     */
    public static <T> Result<T> create(boolean success, String message, T data) {
        return new Result<>(success, data, message);
    }

    /**
     * 结果集转换
     *
     * @since 1.0 Created by lipangeng on 16/4/6 下午1:16. Email:lipg@outlook.com.
     */
    public static Result valueOf(WFXResult<Boolean> result) {
        if (result == null || result.getResult() == null) {
            return Result.create(false, 500, "无法解析处理结果");
        }
        return Result.create(result.getResult(), result.getResultCode(), result.getResultMsg());
    }

    public boolean isSuccess() {
        return success;
    }

    public Result setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public Result setHasError(boolean hasError) {
        return setSuccess(! hasError);
    }

    public boolean hasError() {
        return ! success;
    }

    public T getData() {
        return data;
    }

    public Result<T> setData(T data) {
        this.data = data;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Result<T> setMessage(String message) {
        this.message = message;
        return this;
    }
}


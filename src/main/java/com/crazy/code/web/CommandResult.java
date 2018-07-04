/**
 * 系统模块操作结果类
 *     统一定义封装各个模块Controller层操作结果信息，不使用BindingResult时可以使用该类
 * 对象携带处理结果信息。
 */
package com.crazy.code.web;

import org.springframework.validation.DefaultMessageCodesResolver;
import org.springframework.validation.Errors;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.ObjectError;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author huangshuoying
 */
public class CommandResult<T> implements Serializable {
    private static final long serialVersionUID = -3075581989336398497L;

    /** 操作是否成功 */
    private boolean success;

    /** 操作结果标题 */
    private String title;

    /** 操作成功时返回的提示信息，失败时从errors中获取 */
    private String message;

    private final String objectName;

    private final List<ObjectError> errors = new LinkedList<>();

    /** 结果对象 */
    private T res;

    /** Token 会话标识字符串 */
    private String token;

    /**
     * Create a new CommandResult instance.
     * @param objectName the name of the target object
     * @see DefaultMessageCodesResolver
     */
    public CommandResult(String objectName) {
        this.objectName = objectName;
        this.success = false;
        this.res = null;
        this.token = null;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
        this.title = success ? "JGLOBAL_COMMAND_SUCCESS" : "JGLOBAL_COMMAND_FAILED";
    }

    public T getRes() {
        return res;
    }

    public void setRes(T res) {
        this.res = res;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Return the strategy to use for resolving errors into message codes.
     */
    public MessageCodesResolver createMessageCodesResolver() {
        return new DefaultMessageCodesResolver();
    }

    public String getObjectName() {
        return this.objectName;
    }

    /**
     * Add a custom {@link ObjectError} to the errors list.
     * @see ObjectError
     */
    public void addError(ObjectError error) {
        this.errors.add(error);
        setSuccess(false);
    }

    /**
     * Register a global error for the entire target object,
     * using the given error description.
     * @param errorCode error code, interpretable as a message key
     */
    public void reject(String errorCode) {
        reject(errorCode, null, null);
    }

    /**
     * Register a global error for the entire target object,
     * using the given error description.
     * @param errorCode error code, interpretable as a message key
     * @param defaultMessage fallback default message
     */
    public void reject(String errorCode, String defaultMessage) {
        reject(errorCode, null, defaultMessage);
    }

    /**
     * Register a global error for the entire target object,
     * using the given error description.
     * @param errorCode error code, interpretable as a message key
     * @param errorArgs error arguments, for argument binding via MessageFormat
     * (can be {@code null})
     * @param defaultMessage fallback default message
     */
    public void reject(String errorCode, Object[] errorArgs, String defaultMessage) {
        addError(new ObjectError(getObjectName(), resolveMessageCodes(errorCode), errorArgs, defaultMessage));
    }

    /**
     * Add all errors from the given {@code Errors} instance to this
     * {@code Errors} instance.
     * <p>This is a onvenience method to avoid repeated {@code reject(..)}
     * calls for merging an {@code Errors} instance into another
     * {@code Errors} instance.
     * <p>Note that the passed-in {@code Errors} instance is supposed
     * to refer to the same target object, or at least contain compatible errors
     * that apply to the target object of this {@code Errors} instance.
     * @param errors the {@code Errors} instance to merge in
     */
    public void addAllErrors(Errors errors) {
        if (!errors.getObjectName().equals(getObjectName())) {
            throw new IllegalArgumentException("Errors object needs to have same object name");
        }
        if (errors.hasErrors()) {
            this.errors.addAll(errors.getAllErrors());
            setSuccess(false);
        } else {
            setSuccess(true);
        }
    }

    /**
     * Resolve the given error code into message codes.
     * <p>Calls the configured {@link MessageCodesResolver} with appropriate parameters.
     * @param errorCode the error code to resolve into message codes
     * @return the resolved message codes
     */
    public String[] resolveMessageCodes(String errorCode) {
        return createMessageCodesResolver().resolveMessageCodes(errorCode, getObjectName());
    }

    /**
     * Return if there were any errors.
     */
    public boolean hasErrors() {
        return !this.errors.isEmpty();
    }

    /**
     * Return the total number of errors.
     */
    public int getErrorCount() {
        return this.errors.size();
    }

    /**
     * Get all errors, both global and field ones.
     * @return List of {@link ObjectError} instances
     */
    public List<ObjectError> getErrors() {
        return Collections.unmodifiableList(this.errors);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

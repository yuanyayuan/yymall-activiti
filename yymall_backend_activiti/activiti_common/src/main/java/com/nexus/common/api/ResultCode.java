package com.nexus.common.api;
/**
 * @className ResultCode
 * @description 返回结果状态标志枚举类
 * @author LiYuan
 * @date 2020/10/28
**/
public enum ResultCode implements IErrorCode {
    //操作成功
    SUCCESS(200, "操作成功"),
    //操作失败
    FAILED(500, "操作失败"),
    //spring security 校验
    UNAUTHORIZED(401,"暂未登录或token已经过期"),
    PERMISSION_FORBIDDEN(403,"没有相关权限"),
    //通用的错误类型10000开头
    NO_OBJECT_FOUND(10001,"请求对象不存在"),
    UNKNOWN_ERROR(10002,"未知错误"),
    NO_HANDLER_FOUND(10003,"找不到执行的路径操作"),
    PARAMETER_VALIDATION_ERROR(10004,"请求参数错误"),
    STATE_EXCEPTION_ERROR(1005,"无效状态异常"),
    OBJECT_HAS_EXIST(10006,"请求对象已存在"),
    //用户服务相关的错误类型20000开头
    REGISTER_DUP_FAIL(20001,"用户已存在"),
    LOGIN_FAIL(20002,"用户名或密码错误"),
    USER_ACCOUNT_EXPIRED(20002, "账号已过期"),
    USER_CREDENTIALS_ERROR(20003, "密码错误"),
    USER_CREDENTIALS_EXPIRED(20004, "密码过期"),
    USER_ACCOUNT_DISABLE(20005, "账号不可用"),
    USER_ACCOUNT_LOCKED(20006, "账号被锁定"),
    USER_ACCOUNT_NOT_EXIST(20007, "账号不存在"),
    USER_ACCOUNT_ALREADY_EXIST(20008, "账号已存在"),
    USER_ACCOUNT_USE_BY_OTHERS(20009, "账号下线"),
    //admin相关错误
    ADMIN_SHOULD_LOGIN(30001,"管理员需要先登录"),
    //工作流相关
    PROCESSDEFINITION_GET_ERROR(1,"获取流程定义失败"),
    PROCESSDEFINITION_CREATE_ERROR(1,"创建流程定义失败"),
    PROCESSDEPLOYMENT_GET_ERROR(1,"获取流程部署失败"),
    PROCESSINSTANCE_GET_ERROR(1,"获取流程实例失败"),
    PROCESSINSTANCE_CREATE_ERROR(1,"创建流程实例失败"),
    POCESSINSTANCE_SUSPEND_ERROR(1,"挂起流程实例失败"),
    POCESSINSTANCE_RESUME_ERROR(1,"激活流程实例失败"),
    POCESSINSTANCE_DELETE_ERROR(1,"删除流程实例失败"),
    POCESSINSTANCE_GETPARAM_ERROR(1,"获取流程参数失败");

    private long code;
    private String message;

    ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public long getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

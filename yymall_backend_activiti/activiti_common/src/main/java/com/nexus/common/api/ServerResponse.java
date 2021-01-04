package com.nexus.common.api;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**

* @Description:    自定义响应数据结构

* @Author:         Nexus

* @CreateDate:     2020/9/2 23:10

* @UpdateUser:     Nexus 

* @UpdateDate:     2020/9/2 23:10

* @UpdateRemark:   修改内容

* @Version:        1.0

*/
@ApiModel(value = "返回JSON对象")
@Data
@NoArgsConstructor
public class ServerResponse<T>{
    @ApiModelProperty(value = "状态码；200:成功",example = "200")
    private long code;
    @ApiModelProperty(value = "状态说明")
    private String message;
    @ApiModelProperty(value = "返回数据")
    private T data;

    private ServerResponse(long code) {
        this.code = code;
    }

    private ServerResponse(long code, T data) {
        this.code = code;
        this.data = data;
    }

    private ServerResponse(long code, String message) {
        this.code = code;
        this.message = message;
    }

    private ServerResponse(long code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * @Author LiYuan
     * @Description 成功返回结果
     * @Date 10:36 2020/6/1
     * @Param []
     * @return com.snpas.pom.api.ServerResponse<T>
     **/
    public static <T> ServerResponse<T> success(){
        return new ServerResponse<T>(ResultCode.SUCCESS.getCode(),ResultCode.SUCCESS.getMessage());
    }

    /**
     * @Author LiYuan
     * @Description 成功返回结果
     * @Date 10:33 2020/6/1
     * @Param [data 获取的数据]
     * @return com.snpas.pom.api.ServerResponse<T>
     **/
    public static <T> ServerResponse<T> success(T data){
        return new ServerResponse<T>(ResultCode.SUCCESS.getCode(),ResultCode.SUCCESS.getMessage(),data);
    }
    /**
     * @Author LiYuan
     * @Description 成功返回结果
     * @Date 10:35 2020/6/1
     * @Param [data 获取的数据, message 提示信息]
     * @return com.snpas.pom.api.ServerResponse<T>
     **/
    public static <T> ServerResponse<T> success(T data,String message){
        return new ServerResponse<T>(ResultCode.SUCCESS.getCode(),message,data);
    }
    /**
     * @Author LiYuan
     * @Description 失败返回结果
     * @Date 10:42 2020/6/1
     * @Param []
     * @return com.snpas.pom.api.ServerResponse<T>
     **/
    public static <T> ServerResponse<T> failed() {
        return failed(ResultCode.FAILED);
    }
    /**
     * @Author LiYuan
     * @Description 失败返回结果
     * @Date 10:40 2020/6/1
     * @Param [errorCode]
     * @return com.snpas.pom.api.ServerResponse<T>
     **/
    public static <T> ServerResponse<T> failed(IErrorCode errorCode){
        return new ServerResponse<T>(errorCode.getCode(), errorCode.getMessage());
    }
    /**
     * @Author LiYuan
     * @Description 失败返回结果
     * @Date 10:41 2020/6/1
     * @Param [message]
     * @return com.snpas.pom.api.ServerResponse<T>
     **/
    public static <T> ServerResponse<T> failed(String message) {
        return new ServerResponse<T>(ResultCode.FAILED.getCode(), message);
    }

    /**
     * @Author LiYuan
     * @Description 失败返回结果
     * @Date 13:32 2020/6/17
     * @param message
     * @param data
     * @return com.snpas.pom.api.ServerResponse<T>
     **/
    public static <T> ServerResponse<T> failed(String message,T data) {
        return new ServerResponse<T>(ResultCode.FAILED.getCode(), message, data);
    }
    /**
     * @Author : Nexus
     * @Description :
     * @Date : 2020/10/15 22:31
     * @Param : data
     * @return : com.nexus.mall.common.api.ServerResponse<T>
     **/
    public static <T> ServerResponse<T> failed(T data) {
        return new ServerResponse<T>(ResultCode.FAILED.getCode(), ResultCode.FAILED.getMessage(), data);
    }
    /**
     * @Author LiYuan
     * @Description 失败返回结果
     * @Date 10:41 2020/6/1
     * @Param [errorCode, message]
     * @return com.snpas.pom.api.ServerResponse<T>
     **/
    public static <T> ServerResponse<T> failed(IErrorCode errorCode,String message){
        return new ServerResponse<T>(errorCode.getCode(), message);
    }

    /**
     * @Author LiYuan
     * @Description 参数验证失败返回结果
     * @Date 10:43 2020/6/1
     * @Param []
     * @return com.snpas.pom.api.ServerResponse<T>
     **/
    public static <T> ServerResponse<T> validateFailed() {
        return failed(ResultCode.PARAMETER_VALIDATION_ERROR);
    }

    /**
     * @Author LiYuan
     * @Description 参数验证失败返回结果
     * @Date 10:45 2020/6/1
     * @Param [message]
     * @return com.snpas.pom.api.ServerResponse<T>
     **/
    public static <T> ServerResponse<T> validateFailed(String message) {
        return new ServerResponse<T>(ResultCode.PARAMETER_VALIDATION_ERROR.getCode(), message);
    }

    /**
     * @Author LiYuan
     * @Description 未登录返回结果
     * @Date 10:45 2020/6/1
     * @Param [data]
     * @return com.snpas.pom.api.ServerResponse<T>
     **/
    public static <T> ServerResponse<T> unauthorized(T data) {
        return new ServerResponse<T>(ResultCode.UNAUTHORIZED.getCode(), ResultCode.UNAUTHORIZED.getMessage(), data);
    }
    /**
     *
     * loginFail
     *
     * @Author LiYuan
     * @Description 用户名或密码错误
     * @Date 17:31 2020/9/11
     * @param
     * @return com.nexus.mall.common.api.ServerResponse<T>
    **/
    public static <T> ServerResponse<T> loginFail(){
        return new ServerResponse<T>(ResultCode.LOGIN_FAIL.getCode(), ResultCode.LOGIN_FAIL.getMessage());
    }


    /**
     * @Author LiYuan
     * @Description 未授权返回结果
     * @Date 10:45 2020/6/1
     * @Param [data]
     * @return com.snpas.pom.api.ServerResponse<T>
     **/
    public static <T> ServerResponse<T> forbidden(T data) {
        return new ServerResponse<T>(ResultCode.PERMISSION_FORBIDDEN.getCode(), ResultCode.PERMISSION_FORBIDDEN.getMessage(),data);
    }
}

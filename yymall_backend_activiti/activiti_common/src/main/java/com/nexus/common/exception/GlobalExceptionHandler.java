package com.nexus.common.exception;


import com.nexus.common.api.ResultCode;
import com.nexus.common.api.ServerResponse;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolationException;

/**

* @Description:    全局异常处理

* @Author:         Nexus

* @CreateDate:     2020/9/2 23:20

* @UpdateUser:     Nexus

* @UpdateDate:     2020/9/2 23:20

* @UpdateRemark:   修改内容

* @Version:        1.0

*/
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final String BLANK_STR = "null";

    @ResponseBody
    @ExceptionHandler(value = ApiException.class)
    public ServerResponse handle(ApiException e) {
        if (e.getErrorCode() != null) {
            return ServerResponse.failed(e.getErrorCode());
        }
        return ServerResponse.failed(e.getMessage());
    }
    @ResponseBody
    @ExceptionHandler(value = NoHandlerFoundException.class)
    public ServerResponse noHandlerFound(NoHandlerFoundException e){
        return ServerResponse.failed(ResultCode.NO_HANDLER_FOUND, String.valueOf(e.getCause()));
    }
    @ResponseBody
    @ExceptionHandler(value = IllegalArgumentException.class)
    public ServerResponse noHandlerFound(IllegalArgumentException e){
        return ServerResponse.failed(ResultCode.PARAMETER_VALIDATION_ERROR, String.valueOf(e.getCause()));
    }
    @ResponseBody
    @ExceptionHandler(value = IllegalStateException.class)
    public ServerResponse noHandlerFound(IllegalStateException e){
        return ServerResponse.failed(ResultCode.STATE_EXCEPTION_ERROR, String.valueOf(e.getCause()));
    }
    /**
     *
     * 请求参数错误
     *
     * @Author LiYuan
     * @Description 请求参数错误
     * @Date 15:45 2020/11/2
     * @param e
     * @return com.nexus.mall.common.api.ServerResponse
    **/
    @ResponseBody
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public ServerResponse noHandlerFound(MissingServletRequestParameterException e){
        if(!BLANK_STR.equals(String.valueOf(e.getCause()))){
            return ServerResponse.failed(ResultCode.PARAMETER_VALIDATION_ERROR, String.valueOf(e.getCause()));
        }else {
            return ServerResponse.failed(ResultCode.PARAMETER_VALIDATION_ERROR);
        }
    }
    /**
     * post请求参数校验抛出的异常
     * @Author : Nexus
     * @Description : post请求参数校验抛出的异常
     * @Date : 2020/9/7 23:25
     * @Param : e
     * @return : com.nexus.mall.common.api.ServerResponse
     **/
    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ServerResponse handleValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        String message = null;
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                message = fieldError.getField()+fieldError.getDefaultMessage();
            }
        }
        return ServerResponse.validateFailed(message);
    }
    /**
     * get请求参数校验抛出的异常
     * @Author : Nexus
     * @Description : get请求参数校验抛出的异常
     * @Date : 2020/9/7 23:26
     * @Param : e
     * @return : com.nexus.mall.common.api.ServerResponse
     **/
    @ResponseBody
    @ExceptionHandler(value = BindException.class)
    public ServerResponse handleValidException(BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        String message = null;
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                message = fieldError.getField()+fieldError.getDefaultMessage();
            }
        }
        return ServerResponse.validateFailed(message);
    }
    /**
     * 请求方法中校验抛出的异常
     * @Author : Nexus
     * @Description : 请求方法中校验抛出的异常
     * @Date : 2020/9/7 23:26
     * @Param : e
     * @return : com.nexus.mall.common.api.ServerResponse
     **/
    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    public ServerResponse constraintViolationExceptionHandler(ConstraintViolationException e){
        //获取异常中第一个错误信息
        String message = e.getConstraintViolations().iterator().next().getMessage();
        return ServerResponse.validateFailed(message);
    }

    /**
     * 上传文件超过500k，捕获异常：MaxUploadSizeExceededException
     * @Author : Nexus
     * @Description : 上传文件超过500k，捕获异常：MaxUploadSizeExceededException
     * @Date : 2020/11/21 18:40
     * @Param : ex
     * @return : com.nexus.mall.common.api.ServerResponse
     **/
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ServerResponse handlerMaxUploadFile(MaxUploadSizeExceededException ex) {
        return ServerResponse.failed("文件上传大小不能超过500k，请压缩图片或者降低图片质量再上传！");
    }
}

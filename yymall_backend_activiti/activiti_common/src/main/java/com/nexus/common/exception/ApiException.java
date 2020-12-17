package com.nexus.common.exception;


import com.nexus.common.api.IErrorCode;

/**

* @Description:    自定义API异常

* @Author:         Nexus

* @CreateDate:     2020/9/2 23:33

* @UpdateUser:     Nexus 

* @UpdateDate:     2020/9/2 23:33

* @UpdateRemark:   修改内容

* @Version:        1.0

*/
public class ApiException extends RuntimeException{
    private IErrorCode errorCode;

    public ApiException(IErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
    public ApiException(String message) {
        super(message);
    }

    public ApiException(Throwable cause) {
        super(cause);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public IErrorCode getErrorCode() {
        return errorCode;
    }
}

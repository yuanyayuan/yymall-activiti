package com.nexus.common.exception;


import com.nexus.common.api.IErrorCode;

/**

* @Description:    断言处理类，用于抛出各种API异常

* @Author:         Nexus

* @CreateDate:     2020/9/2 23:19

* @UpdateUser:     Nexus

* @UpdateDate:     2020/9/2 23:19

* @UpdateRemark:   修改内容

* @Version:        1.0

*/
public class Asserts {
    public static void fail(String message) {
        throw new ApiException(message);
    }

    public static void fail(IErrorCode errorCode) {
        throw new ApiException(errorCode);
    }
}

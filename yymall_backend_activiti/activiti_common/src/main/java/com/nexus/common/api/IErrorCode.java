package com.nexus.common.api;
/**

* @Description:    封装API的错误码

* @Author:         Nexus

* @CreateDate:     2020/9/2 23:13

* @UpdateUser:     Nexus 

* @UpdateDate:     2020/9/2 23:13

* @UpdateRemark:   修改内容

* @Version:        1.0

*/
public interface IErrorCode {
    long getCode();

    String getMessage();
}

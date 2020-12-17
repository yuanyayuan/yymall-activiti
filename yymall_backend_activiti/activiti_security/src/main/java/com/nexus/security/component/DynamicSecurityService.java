package com.nexus.security.component;

import org.springframework.security.access.ConfigAttribute;

import java.util.Map;

/**

* @Description:    动态权限相关业务类

* @Author:         Nexus

* @CreateDate:     2020/9/10 23:06

* @UpdateUser:     Nexus

* @UpdateDate:     2020/9/10 23:06

* @UpdateRemark:   修改内容

* @Version:        1.0

*/
public interface DynamicSecurityService {
    /**
     * 加载资源ANT通配符和资源对应MAP
     */
    Map<String, ConfigAttribute> loadDataSource();
}

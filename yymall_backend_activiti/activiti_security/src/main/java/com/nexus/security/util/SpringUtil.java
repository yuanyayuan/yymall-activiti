package com.nexus.security.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**

* @Description:    参考macro的Spring工具类

* @Author:         Nexus

* @CreateDate:     2020/9/9 22:42

* @UpdateUser:     Nexus 

* @UpdateDate:     2020/9/9 22:42

* @UpdateRemark:   修改内容

* @Version:        1.0

*/
@Component
public class SpringUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    /**
     * getApplicationContext
     * @Author : Nexus
     * @Description : 获取applicationContext
     * @Date : 2020/9/9 21:58
     * @Param : 
     * @return : org.springframework.context.ApplicationContext
     **/
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringUtil.applicationContext == null) {
            SpringUtil.applicationContext = applicationContext;
        }
    }

    /**
     * getBean
     * @Author : Nexus
     * @Description : 通过name获取Bean
     * @Date : 2020/9/9 21:58
     * @Param : name
     * @return : java.lang.Object
     **/
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    /**
     * getBean
     * @Author : Nexus
     * @Description : 通过class获取Bean
     * @Date : 2020/9/9 21:58
     * @Param : clazz
     * @return : T
     **/
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    /**
     * getBean
     * @Author : Nexus
     * @Description : 通过class获取Bean
     * @Date : 2020/9/9 21:58
     * @Param : name
     * @Param : clazz
     * @return : T
     **/
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }

}

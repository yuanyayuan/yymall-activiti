package com.nexus.api.filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**

* @Description:    java类作用描述

* @Author:         Nexus

* @CreateDate:     2020/9/8 23:15

* @UpdateUser:     Nexus 

* @UpdateDate:     2020/9/8 23:15

* @UpdateRemark:   修改内容

* @Version:        1.0

*/
@Configuration
public class GlobalCorsConfig {
    /**
     * 允许跨域调用的过滤器
     */
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        //允许所有域名进行跨域调用
        /*config.addAllowedOrigin("localhost:9080");*/
        config.addAllowedOrigin("*");
        //允许跨越发送cookie
        config.setAllowCredentials(true);
        //放行全部原始头信息
        config.addAllowedHeader("*");
        //允许所有请求方法跨域调用
        config.addAllowedMethod("*");
        //为url添加映射路径
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        //返回定义好的source
        return new CorsFilter(source);
    }
}

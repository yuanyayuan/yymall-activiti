package com.nexus.api.config;


import com.nexus.common.config.BaseSwaggerConfig;
import com.nexus.common.domain.SwaggerProperties;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**

* @Description:    Swagger API文档相关配置

* @Author:         Nexus

* @CreateDate:     2020/9/3 22:05

* @UpdateUser:     Nexus

* @UpdateDate:     2020/9/3 22:05

* @UpdateRemark:   修改内容

* @Version:        1.0

*/
@Configuration
@EnableSwagger2
public class SwaggerConfig extends BaseSwaggerConfig {
    /**
     * @Author Noctis
     * @Description Swagger API文档相关配置
     * @Date 2020/9/3 22:06
     * @param
     * @return com.nexus.mall.common.domain.SwaggerProperties
     **/
    @Override
    public SwaggerProperties swaggerProperties() {
        return SwaggerProperties.builder()
                .apiBasePackage("com.nexus.api.controller")
                .title("activiti-api模块")
                .description("工作流相关接口文档")
                .contactName("nexus")
                .contactEmail("liyuan0707@outlook.com")
                .contactUrl("https://github.com/yuanyayuan/yymall")
                .version("1.0")
                .enableSecurity(true)
                .build();
    }
}

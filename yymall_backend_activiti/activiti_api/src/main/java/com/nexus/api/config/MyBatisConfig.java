package com.nexus.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

@Configuration
@EnableTransactionManagement
@MapperScan({"com.nexus.dao.mapper","com.nexus.dao.mapper.custom"})
public class MyBatisConfig {
}

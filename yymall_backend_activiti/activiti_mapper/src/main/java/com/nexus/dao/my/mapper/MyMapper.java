package com.nexus.dao.my.mapper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**

* @Description:    java类作用描述

* @Author:         Nexus

* @CreateDate:     2020/9/4 22:38

* @UpdateUser:     Nexus 

* @UpdateDate:     2020/9/4 22:38

* @UpdateRemark:   修改内容

* @Version:        1.0

*/
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T>{
}

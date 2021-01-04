package com.nexus.dao.mapper.custom;

import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

public interface ActivitiMapperCustom {


    List<HashMap<String,Object>> selectFormDate(@Param("PROC_INST_ID") String PROC_INST_ID);

    int insertFormData(@Param("maps")List<HashMap<String,Object>> maps);

}

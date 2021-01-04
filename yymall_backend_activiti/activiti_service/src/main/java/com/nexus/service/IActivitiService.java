package com.nexus.service;

import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

public interface IActivitiService {

    List<HashMap<String,Object>> selectFormDate(String PROC_INST_ID);

    int insertFormData(List<HashMap<String, Object>> listMap);
}

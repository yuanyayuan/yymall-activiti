package com.nexus.service.impl;

import com.nexus.dao.mapper.custom.ActivitiMapperCustom;
import com.nexus.service.IActivitiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class ActivitiServiceImpl implements IActivitiService {
    @Autowired
    private ActivitiMapperCustom activitiMapperDap;

    @Override
    public List<HashMap<String, Object>> selectFormDate(String PROC_INST_ID) {
        return activitiMapperDap.selectFormDate(PROC_INST_ID);
    }

    @Override
    public int insertFormData(List<HashMap<String, Object>> listMap) {
        return activitiMapperDap.insertFormData(listMap);
    }
}

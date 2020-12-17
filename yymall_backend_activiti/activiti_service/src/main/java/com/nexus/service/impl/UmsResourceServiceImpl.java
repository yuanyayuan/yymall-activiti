package com.nexus.service.impl;

import com.nexus.dao.mapper.UmsResourceMapper;
import com.nexus.pojo.UmsResource;
import com.nexus.service.IUmsResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UmsResourceServiceImpl implements IUmsResourceService {
    @Autowired
    private UmsResourceMapper resourceMapper;
    @Override
    public List<UmsResource> listAll() {
        return resourceMapper.selectAll();
    }
}

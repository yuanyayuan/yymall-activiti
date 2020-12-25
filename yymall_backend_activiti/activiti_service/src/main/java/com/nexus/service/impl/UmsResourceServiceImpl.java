package com.nexus.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.nexus.dao.mapper.UmsResourceMapper;
import com.nexus.pojo.UmsResource;
import com.nexus.service.IUmsResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Service
public class UmsResourceServiceImpl implements IUmsResourceService {
    @Autowired
    private UmsResourceMapper resourceMapper;
    @Override
    public List<UmsResource> listAll() {
        return resourceMapper.selectAll();
    }

    /**
     * 添加资源
     *
     * @param umsResource
     * @return int
     * @Author LiYuan
     * @Description 添加资源
     * @Date 10:07 2020/12/7
     **/
    @Override
    public int create(UmsResource umsResource) {
        umsResource.setCreateTime(new Date());
        return resourceMapper.insert(umsResource);
    }

    /**
     * 修改资源
     *
     * @param id
     * @param umsResource
     * @return int
     * @Author LiYuan
     * @Description 修改资源
     * @Date 10:08 2020/12/7
     **/
    @Override
    public int update(Long id, UmsResource umsResource) {
        umsResource.setId(id);
        return resourceMapper.updateByPrimaryKeySelective(umsResource);
    }

    /**
     * 获取资源详情
     *
     * @param id
     * @return com.snpas.pmo.pojo.UmsResource
     * @Author LiYuan
     * @Description 获取资源详情
     * @Date 10:30 2020/12/7
     **/
    @Override
    public UmsResource getItem(Long id) {
        return resourceMapper.selectByPrimaryKey(id);
    }

    /**
     * 删除资源
     *
     * @param id
     * @return int
     * @Author LiYuan
     * @Description 删除资源
     * @Date 10:30 2020/12/7
     **/
    @Override
    public int delete(Long id) {
        return resourceMapper.deleteByPrimaryKey(id);
    }

    /**
     * 分页查询资源
     *
     * @param categoryId
     * @param nameKeyword
     * @param urlKeyword
     * @param page
     * @param pageSize
     * @return java.util.List<com.snpas.pmo.pojo.UmsResource>
     * @Author LiYuan
     * @Description 分页查询资源
     * @Date 10:30 2020/12/7
     **/
    @Override
    public List<UmsResource> list(Long categoryId,
                                  String nameKeyword,
                                  String urlKeyword,
                                  Integer page,
                                  Integer pageSize) {
        Example example = new Example(UmsResource.class);
        Example.Criteria criteria = example.createCriteria();
        if(categoryId!=null){
            criteria.andEqualTo("categoryId",categoryId);
        }
        if(StrUtil.isNotEmpty(nameKeyword)){
            criteria.andLike("name",'%'+nameKeyword+'%');
        }
        if(StrUtil.isNotEmpty(urlKeyword)){
            criteria.andLike("url",'%'+urlKeyword+'%');
        }
        PageHelper.startPage(page,pageSize);
        return resourceMapper.selectByExample(example);
    }
}

package com.nexus.service.impl;

import com.nexus.dao.mapper.UmsResourceCategoryMapper;
import com.nexus.pojo.UmsResourceCategory;
import com.nexus.service.IUmsResourceCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * @className UmsResourceCategoryServiceImpl
 * @description 后台资源分类管理Service实现类
 * @author LiYuan
 * @date 2020/12/7
**/
@Service
public class UmsResourceCategoryServiceImpl implements IUmsResourceCategoryService {
    @Autowired
    private UmsResourceCategoryMapper resourceCategoryMapper;

    /**
     * 获取所有资源分类
     *
     * @return java.util.List<com.snpas.pmo.pojo.UmsResourceCategory>
     * @Author LiYuan
     * @Description 获取所有资源分类
     * @Date 9:56 2020/12/7
     **/
    @Override
    public List<UmsResourceCategory> listAll() {
        Example example = new Example(UmsResourceCategory.class);
        example.setOrderByClause("sort desc");
        return resourceCategoryMapper.selectByExample(example);
    }

    /**
     * 创建资源分类
     *
     * @param umsResourceCategory
     * @return int
     * @Author LiYuan
     * @Description 创建资源分类
     * @Date 9:56 2020/12/7
     **/
    @Override
    public int create(UmsResourceCategory umsResourceCategory) {
        umsResourceCategory.setCreateTime(new Date());
        return resourceCategoryMapper.insert(umsResourceCategory);
    }

    /**
     * 修改资源分类
     *
     * @param id
     * @param umsResourceCategory
     * @return int
     * @Author LiYuan
     * @Description 修改资源分类
     * @Date 9:56 2020/12/7
     **/
    @Override
    public int update(Long id, UmsResourceCategory umsResourceCategory) {
        umsResourceCategory.setId(id);
        return resourceCategoryMapper.updateByPrimaryKeySelective(umsResourceCategory);
    }

    /**
     * 删除资源分类
     *
     * @param id
     * @return int
     * @Author LiYuan
     * @Description 删除资源分类
     * @Date 9:56 2020/12/7
     **/
    @Override
    public int delete(Long id) {
        return resourceCategoryMapper.deleteByPrimaryKey(id);
    }
}

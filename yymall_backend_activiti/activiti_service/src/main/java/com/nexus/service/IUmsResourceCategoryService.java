package com.nexus.service;



import com.nexus.pojo.UmsResourceCategory;

import java.util.List;

public interface IUmsResourceCategoryService {
    /**
     *
     * 获取所有资源分类
     *
     * @Author LiYuan
     * @Description 获取所有资源分类
     * @Date 9:56 2020/12/7
     * @param
     * @return java.util.List<com.snpas.pmo.pojo.UmsResourceCategory>
    **/
    List<UmsResourceCategory> listAll();

    /**
     *
     * 创建资源分类
     *
     * @Author LiYuan
     * @Description 创建资源分类
     * @Date 9:56 2020/12/7
     * @param umsResourceCategory
     * @return int
    **/
    int create(UmsResourceCategory umsResourceCategory);

    /**
     *
     * 修改资源分类
     *
     * @Author LiYuan
     * @Description 修改资源分类
     * @Date 9:56 2020/12/7
     * @param id
     * @param umsResourceCategory
     * @return int
    **/
    int update(Long id, UmsResourceCategory umsResourceCategory);

    /**
     *
     * 删除资源分类
     *
     * @Author LiYuan
     * @Description 删除资源分类
     * @Date 9:56 2020/12/7
     * @param id
     * @return int
    **/
    int delete(Long id);
}

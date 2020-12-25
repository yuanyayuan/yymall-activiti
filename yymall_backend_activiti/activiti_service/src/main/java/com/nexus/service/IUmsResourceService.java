package com.nexus.service;

import com.nexus.pojo.UmsResource;

import java.util.List;

public interface IUmsResourceService {
    /**
     *
     * 添加资源
     *
     * @Author LiYuan
     * @Description 添加资源
     * @Date 10:07 2020/12/7
     * @param umsResource
     * @return int
     **/
    int create(UmsResource umsResource);

    /**
     *
     * 修改资源
     *
     * @Author LiYuan
     * @Description 修改资源
     * @Date 10:08 2020/12/7
     * @param id
     * @param umsResource
     * @return int
     **/
    int update(Long id, UmsResource umsResource);

    /**
     *
     * 获取资源详情
     *
     * @Author LiYuan
     * @Description 获取资源详情
     * @Date 10:30 2020/12/7
     * @param id
     * @return com.snpas.pmo.pojo.UmsResource
     **/
    UmsResource getItem(Long id);

    /**
     *
     * 删除资源
     *
     * @Author LiYuan
     * @Description 删除资源
     * @Date 10:30 2020/12/7
     * @param id
     * @return int
     **/
    int delete(Long id);

    /**
     *
     * 分页查询资源
     *
     * @Author LiYuan
     * @Description 分页查询资源
     * @Date 10:30 2020/12/7
     * @param categoryId
     * @param nameKeyword
     * @param urlKeyword
     * @param page
     * @param pageSize
     * @return java.util.List<com.snpas.pmo.pojo.UmsResource>
     **/
    List<UmsResource> list(Long categoryId, String nameKeyword, String urlKeyword, Integer page, Integer pageSize);
    /**
     *
     * 查询全部资源
     *
     * @Author LiYuan
     * @Description //TODO
     * @Date 14:12 2020/12/25
     * @param
     * @return java.util.List<com.nexus.pojo.UmsResource>
    **/
    List<UmsResource> listAll();
}

package com.nexus.service;



import com.nexus.pojo.UmsMenu;
import com.nexus.pojo.dto.user.UmsMenuNode;

import java.util.List;

/**
 * @className UmsMenuService
 * @description 后台菜单管理Service
 * @author LiYuan
 * @date 2020/12/4
**/
public interface IUmsMenuService {
    /**
     * 
     * 创建后台菜单
     * 
     * @Author LiYuan
     * @Description 创建后台菜单
     * @Date 10:54 2020/12/4
     * @param umsMenu
     * @return int
    **/
    int create(UmsMenu umsMenu);
    /**
     * 
     * 修改后台菜单
     * 
     * @Author LiYuan
     * @Description 修改后台菜单
     * @Date 10:54 2020/12/4
     * @param id
     * @param umsMenu
     * @return int
    **/
    int update(Long id, UmsMenu umsMenu);
    /**
     *
     * 根据ID获取菜单详情
     *
     * @Author LiYuan
     * @Description 根据ID获取菜单详情
     * @Date 10:54 2020/12/4
     * @param id
     * @return com.snpas.pmo.pojo.UmsUser
    **/
    UmsMenu getItem(Long id);
    /**
     *
     * 根据ID删除菜单
     *
     * @Author LiYuan
     * @Description 根据ID删除菜单
     * @Date 10:55 2020/12/4
     * @param id
     * @return int
    **/
    int delete(Long id);
    /**
     *
     * 分页查询后台菜单
     *
     * @Author LiYuan
     * @Description 分页查询后台菜单
     * @Date 10:55 2020/12/4
     * @param parentId
     * @param page
     * @param pageSize
     * @return java.util.List<com.snpas.pmo.pojo.UmsUser>
    **/
    List<UmsMenu> list(Long parentId, Integer pageNum, Integer pageSize);
    /**
     *
     * 树形结构返回所有菜单列表
     *
     * @Author LiYuan
     * @Description 树形结构返回所有菜单列表
     * @Date 10:55 2020/12/4
     * @param
     * @return java.util.List<com.snpas.pmo.dto.admin.UmsMenuNode>
    **/
    List<UmsMenuNode> treeList();
    /**
     *
     * 修改菜单显示状态
     *
     * @Author LiYuan
     * @Description 修改菜单显示状态
     * @Date 10:55 2020/12/4
     * @param id
     * @param hidden
     * @return int
    **/
    int updateHidden(Long id, Integer hidden);
}

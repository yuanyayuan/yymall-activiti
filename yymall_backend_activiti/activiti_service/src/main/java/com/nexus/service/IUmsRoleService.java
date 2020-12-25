package com.nexus.service;

import com.nexus.pojo.UmsMenu;
import com.nexus.pojo.UmsResource;
import com.nexus.pojo.UmsRole;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IUmsRoleService {
    /**
     * 添加角色
     * @Author LiYuan
     * @Description 添加角色
     * @Date 13:56 2020/11/5
     * @param role
     * @return int
     **/
    int create(UmsRole role);

    /**
     * 修改角色信息
     * @Author LiYuan
     * @Description 修改角色信息
     * @Date 13:56 2020/11/5
     * @param id
     * @param role
     * @return int
     **/
    int update(Long id, UmsRole role);

    /**
     * 批量删除角色
     * @Author LiYuan
     * @Description 批量删除角色
     * @Date 13:56 2020/11/5
     * @param ids
     * @return int
     **/
    int delete(List<Long> ids);

    /**
     * 获取所有角色列表
     * @Author LiYuan
     * @Description 获取所有角色列表
     * @Date 13:57 2020/11/5
     * @param
     * @return java.util.List<com.snpas.pmo.pojo.Role>
     **/
    List<UmsRole> list();

    /**
     * 分页获取角色列表
     * @Author LiYuan
     * @Description 分页获取角色列表
     * @Date 13:57 2020/11/5
     * @param keyword
     * @param pageSize
     * @param pageNum
     * @return java.util.List<com.snpas.pmo.pojo.Role>
     **/
    List<UmsRole> list(String keyword, Integer pageSize, Integer pageNum);
    /**
     * 根据用户id获取对应菜单
     * @Author : Nexus
     * @Description : 根据用户id获取对应菜单
     * @Date : 2020/12/14 23:43
     * @Param : adminId
     * @return : java.util.List<com.nexus.pojo.UmsMenu>
     **/
    List<UmsMenu> getMenuList(Long userId);


    /**
     *
     * 获取角色相关菜单
     *
     * @Author LiYuan
     * @Description 获取角色相关菜单
     * @Date 14:06 2020/11/19
     * @param roleId
     * @return java.util.List<com.snpas.pmo.pojo.UmsMenu>
     **/
    List<UmsMenu> listMenu(Long roleId);

    /**
     *
     * 获取角色相关资源
     *
     * @Author LiYuan
     * @Description 获取角色相关资源
     * @Date 14:07 2020/11/19
     * @param roleId
     * @return java.util.List<com.snpas.pmo.pojo.UmsResource>
     **/
    List<UmsResource> listResource(Long roleId);

    /**
     *
     * 给角色分配菜单
     *
     * @Author LiYuan
     * @Description 给角色分配菜单
     * @Date 14:07 2020/11/19
     * @param roleId
     * @param menuIds
     * @return int
     **/
    @Transactional(rollbackFor = RuntimeException.class)
    int allocMenu(Long roleId, List<Long> menuIds);

    /**
     *
     * 给角色分配资源
     *
     * @Author LiYuan
     * @Description 给角色分配资源
     * @Date 14:07 2020/11/19
     * @param roleId
     * @param resourceIds
     * @return int
     **/
    @Transactional(rollbackFor = RuntimeException.class)
    int allocResource(Long roleId, List<Long> resourceIds);

}

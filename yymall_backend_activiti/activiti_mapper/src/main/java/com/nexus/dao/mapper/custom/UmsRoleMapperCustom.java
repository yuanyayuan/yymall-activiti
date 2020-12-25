package com.nexus.dao.mapper.custom;

import com.nexus.pojo.UmsMenu;
import com.nexus.pojo.UmsResource;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UmsRoleMapperCustom {
    /**
     * 根据后台用户ID获取菜单
     * @Author LiYuan
     * @Description
     * @Date 14:50 2020/12/24
     * @param userId
     * @return java.util.List<com.nexus.pojo.UmsMenu>
    **/
    List<UmsMenu> getMenuList(@Param("userId") Long userId);
    /**
     * 根据角色Id获取菜单列表
     * @Author LiYuan
     * @Description
     * @Date 14:49 2020/12/24
     * @param roleId
     * @return java.util.List<com.nexus.pojo.UmsMenu>
    **/
    List<UmsMenu> getMenuListByRoleId(@Param("roleId") Long roleId);
    /**
     * 根据角色ID获取资源列表
     * @Author LiYuan
     * @Description
     * @Date 14:49 2020/12/24
     * @param roleId
     * @return java.util.List<com.nexus.pojo.UmsResource>
    **/
    List<UmsResource> getResourceListByRoleId(@Param("roleId") Long roleId);
}

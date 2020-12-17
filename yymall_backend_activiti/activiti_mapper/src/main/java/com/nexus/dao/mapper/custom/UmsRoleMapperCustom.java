package com.nexus.dao.mapper.custom;

import com.nexus.pojo.UmsMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UmsRoleMapperCustom {
    /**
     * 根据后台用户ID获取菜单
     * @Author : Nexus
     * @Description : 根据后台用户ID获取菜单
     * @Date : 2020/11/25 22:37
     * @Param : adminId
     * @return : java.util.List<com.nexus.mall.pojo.BackendMenu>
     **/
    List<UmsMenu> getMenuList(@Param("userId") Long userId);
}

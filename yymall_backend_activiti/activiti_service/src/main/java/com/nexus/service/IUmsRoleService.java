package com.nexus.service;

import com.nexus.pojo.UmsMenu;

import java.util.List;

public interface IUmsRoleService {
    /**
     * 根据用户id获取对应菜单
     * @Author : Nexus
     * @Description : 根据用户id获取对应菜单
     * @Date : 2020/12/14 23:43
     * @Param : adminId
     * @return : java.util.List<com.nexus.pojo.UmsMenu>
     **/
    List<UmsMenu> getMenuList(Long userId);
}

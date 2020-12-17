package com.nexus.service.impl;

import com.nexus.dao.mapper.custom.UmsRoleMapperCustom;
import com.nexus.pojo.UmsMenu;
import com.nexus.service.IUmsRoleService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UmsRoleServiceImpl implements IUmsRoleService {

    @Autowired
    private UmsRoleMapperCustom roleMapperDao;

    /**
     * 根据用户id获取对应菜单
     *
     * @param userId
     * @return : java.util.List<com.nexus.pojo.UmsMenu>
     * @Author : Nexus
     * @Description : 根据用户id获取对应菜单
     * @Date : 2020/12/14 23:43
     * @Param : adminId
     */
    @Override
    public List<UmsMenu> getMenuList(Long userId) {
        return roleMapperDao.getMenuList(userId);
    }
}

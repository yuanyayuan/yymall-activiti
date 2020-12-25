package com.nexus.service.impl;

import com.github.pagehelper.PageHelper;
import com.nexus.dao.mapper.UmsRoleMapper;
import com.nexus.dao.mapper.UmsRoleMenuRelationMapper;
import com.nexus.dao.mapper.UmsRoleResourceRelationMapper;
import com.nexus.dao.mapper.custom.UmsRoleMapperCustom;
import com.nexus.pojo.*;
import com.nexus.service.IUmsRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
@Service
public class UmsRoleServiceImpl implements IUmsRoleService {
    @Autowired
    private UmsRoleMapper roleMapper;

    @Autowired
    private UmsRoleMenuRelationMapper roleMenuRelationMapper;

    @Autowired
    private UmsRoleResourceRelationMapper roleResourceRelationMapper;

    @Autowired
    private UmsRoleMapperCustom roleDao;

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
        return roleDao.getMenuList(userId);
    }

    /**
     * 添加角色
     *
     * @param role
     * @return int
     * @Author LiYuan
     * @Description 添加角色
     * @Date 13:56 2020/11/5
     **/
    @Override
    public int create(UmsRole role) {
        role.setCreateTime(new Date());
        role.setAdminCount(0);
        role.setSort(0);
        return roleMapper.insert(role);
    }

    /**
     * 修改角色信息
     *
     * @param id
     * @param role
     * @return int
     * @Author LiYuan
     * @Description 修改角色信息
     * @Date 13:56 2020/11/5
     **/
    @Override
    public int update(Long id, UmsRole role) {
        role.setId(id);
        return roleMapper.updateByPrimaryKeySelective(role);
    }

    /**
     * 批量删除角色
     *
     * @param ids
     * @return int
     * @Author LiYuan
     * @Description 批量删除角色
     * @Date 13:56 2020/11/5
     **/
    @Override
    public int delete(List<Long> ids) {
        Example example = new Example(UmsRole.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id",ids);
        return roleMapper.deleteByExample(example);
    }

    /**
     * 获取所有角色列表
     *
     * @return java.util.List<com.snpas.pmo.pojo.Role>
     * @Author LiYuan
     * @Description 获取所有角色列表
     * @Date 13:57 2020/11/5
     **/
    @Override
    public List<UmsRole> list() {
        return roleMapper.selectByExample(new Example(UmsRole.class));
    }

    /**
     * 分页获取角色列表
     *
     * @param keyword
     * @param pageSize
     * @param pageNum
     * @return java.util.List<com.snpas.pmo.pojo.Role>
     * @Author LiYuan
     * @Description 分页获取角色列表
     * @Date 13:57 2020/11/5
     **/
    @Override
    public List<UmsRole> list(String keyword, Integer pageSize, Integer pageNum) {
        Example example = new Example(UmsRole.class);
        Example.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(keyword)){
            criteria.andLike("name","%"+ keyword + "%");
        }
        PageHelper.startPage(pageNum, pageSize);
        return roleMapper.selectByExample(example);
    }

    /**
     * 获取角色相关菜单
     *
     * @param roleId
     * @return java.util.List<com.snpas.pmo.pojo.UmsMenu>
     * @Author LiYuan
     * @Description 获取角色相关菜单
     * @Date 14:06 2020/11/19
     **/
    @Override
    public List<UmsMenu> listMenu(Long roleId) {
        return roleDao.getMenuListByRoleId(roleId);
    }

    /**
     * 获取角色相关资源
     *
     * @param roleId
     * @return java.util.List<com.snpas.pmo.pojo.UmsResource>
     * @Author LiYuan
     * @Description 获取角色相关资源
     * @Date 14:07 2020/11/19
     **/
    @Override
    public List<UmsResource> listResource(Long roleId) {
        return roleDao.getResourceListByRoleId(roleId);
    }

    /**
     * 给角色分配菜单
     *
     * @param roleId
     * @param menuIds
     * @return int
     * @Author LiYuan
     * @Description 给角色分配菜单
     * @Date 14:07 2020/11/19
     **/
    @Override
    public int allocMenu(Long roleId, List<Long> menuIds) {
        //先删除原有关系
        Example example = new Example(UmsRoleMenuRelation.class);
        example.createCriteria().andEqualTo("roleId",roleId);
        roleMenuRelationMapper.deleteByExample(example);
        //批量插入新关系
        for (Long menuId : menuIds) {
            UmsRoleMenuRelation relation = new UmsRoleMenuRelation();
            relation.setRoleId(roleId);
            relation.setMenuId(menuId);
            roleMenuRelationMapper.insert(relation);
        }
        return menuIds.size();
    }

    /**
     * 给角色分配资源
     *
     * @param roleId
     * @param resourceIds
     * @return int
     * @Author LiYuan
     * @Description 给角色分配资源
     * @Date 14:07 2020/11/19
     **/
    @Override
    public int allocResource(Long roleId, List<Long> resourceIds) {
        //先删除原有关系
        Example example=new Example(UmsRoleResourceRelation.class);
        example.createCriteria().andEqualTo("roleId",roleId);
        roleResourceRelationMapper.deleteByExample(example);
        //批量插入新关系
        for (Long resourceId : resourceIds) {
            UmsRoleResourceRelation relation = new UmsRoleResourceRelation();
            relation.setRoleId(roleId);
            relation.setResourceId(resourceId);
            roleResourceRelationMapper.insert(relation);
        }
        return resourceIds.size();
    }
}

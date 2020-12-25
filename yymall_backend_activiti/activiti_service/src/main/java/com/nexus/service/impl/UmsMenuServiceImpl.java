package com.nexus.service.impl;

import com.github.pagehelper.PageHelper;
import com.nexus.dao.mapper.UmsMenuMapper;
import com.nexus.pojo.UmsMenu;
import com.nexus.pojo.dto.user.UmsMenuNode;
import com.nexus.service.IUmsMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
public class UmsMenuServiceImpl implements IUmsMenuService {
    @Autowired
    private UmsMenuMapper menuMapper;

    /**
     * 创建后台菜单
     * @param umsMenu
     * @return int
     * @Author LiYuan
     * @Description 创建后台菜单
     * @Date 10:54 2020/12/4
     **/
    @Override
    public int create(UmsMenu umsMenu) {
        umsMenu.setCreateTime(new Date());
        updateLevel(umsMenu);
        return menuMapper.insert(umsMenu);
    }

    /**
     * 修改菜单层级
     * @Author LiYuan
     * @Description 修改菜单层级
     * @Date 11:08 2020/12/4
     * @param umsMenu
     * @return void
    **/
    private void updateLevel(UmsMenu umsMenu) {
        if (umsMenu.getParentId() == 0) {
            //没有父菜单时为一级菜单
            umsMenu.setLevel(0);
        } else {
            //有父菜单时选择根据父菜单level设置
            UmsMenu parentMenu = menuMapper.selectByPrimaryKey(umsMenu.getParentId());
            if (parentMenu != null) {
                umsMenu.setLevel(parentMenu.getLevel() + 1);
            } else {
                umsMenu.setLevel(0);
            }
        }
    }

    /**
     * 修改后台菜单
     * @param id
     * @param umsMenu
     * @return int
     * @Author LiYuan
     * @Description 修改后台菜单
     * @Date 10:54 2020/12/4
     **/
    @Override
    public int update(Long id, UmsMenu umsMenu) {
        umsMenu.setId(id);
        updateLevel(umsMenu);
        return menuMapper.updateByPrimaryKeySelective(umsMenu);
    }

    /**
     * 根据ID获取菜单详情
     *
     * @param id
     * @return com.snpas.pmo.pojo.UmsUser
     * @Author LiYuan
     * @Description 根据ID获取菜单详情
     * @Date 10:54 2020/12/4
     **/
    @Override
    public UmsMenu getItem(Long id) {
        return menuMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据ID删除菜单
     *
     * @param id
     * @return int
     * @Author LiYuan
     * @Description 根据ID删除菜单
     * @Date 10:55 2020/12/4
     **/
    @Override
    public int delete(Long id) {
        return menuMapper.deleteByPrimaryKey(id);
    }

    /**
     * 分页查询后台菜单
     *
     * @param parentId
     * @param pageNum
     * @param pageSize
     * @return java.util.List<com.snpas.pmo.pojo.UmsUser>
     * @Author LiYuan
     * @Description 分页查询后台菜单
     * @Date 10:55 2020/12/4
     **/
    @Override
    public List<UmsMenu> list(Long parentId, Integer pageNum, Integer pageSize) {
        Example example = new Example(UmsMenu.class);
        example.createCriteria().andEqualTo("parentId",parentId);
        PageHelper.startPage(pageNum,pageSize);
        return menuMapper.selectByExample(example);
    }

    /**
     * 树形结构返回所有菜单列表
     *
     * @return java.util.List<com.snpas.pmo.dto.admin.UmsMenuNode>
     * @Author LiYuan
     * @Description 树形结构返回所有菜单列表
     * @Date 10:55 2020/12/4
     **/
    @Override
    public List<UmsMenuNode> treeList() {
        List<UmsMenu> menuList = menuMapper.selectByExample(new Example(UmsMenu.class));
        return menuList.stream()
                .filter(umsMenu -> umsMenu.getParentId().equals(0L))
                .map(umsMenu -> coverMenuNode(umsMenu,menuList)).collect(Collectors.toList());
    }

    private UmsMenuNode coverMenuNode(UmsMenu umsMenu,List<UmsMenu> menuList){
        UmsMenuNode node = new UmsMenuNode();
        BeanUtils.copyProperties(umsMenu,node);
        List<UmsMenuNode> children = menuList.stream()
                .filter(subMenu -> subMenu.getParentId().equals(umsMenu.getId()))
                .map(subMenu -> coverMenuNode(subMenu,menuList)).collect(Collectors.toList());
        node.setChildren(children);
        return node;
    }

    /**
     * 修改菜单显示状态
     *
     * @param id
     * @param hidden
     * @return int
     * @Author LiYuan
     * @Description 修改菜单显示状态
     * @Date 10:55 2020/12/4
     **/
    @Override
    public int updateHidden(Long id, Integer hidden) {
       UmsMenu menu = new UmsMenu();
       menu.setId(id);
       menu.setHidden(hidden);
       return menuMapper.updateByPrimaryKeySelective(menu);
    }
}

package com.nexus.dao.mapper.custom;

import com.nexus.pojo.UmsResource;
import com.nexus.pojo.UmsRole;
import com.nexus.pojo.UmsUserRoleRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UmsUserRoleRelationMapperCustom {
    /**
     * 批量插入用户角色关系
     * @Author LiYuan
     * @Description
     * @Date 14:53 2020/12/24
     * @param adminRoleRelationList
     * @return int
    **/
    int insertList(@Param("list") List<UmsUserRoleRelation> adminRoleRelationList);
    /**
     * 获取用户所有可访问资源
     * @Author LiYuan
     * @Description //TODO
     * @Date 14:52 2020/12/24
     * @param userId
     * @return java.util.List<com.nexus.pojo.UmsResource>
    **/
    List<UmsResource> getResourceList(@Param("userId") Long userId);
    /**
     * 根据用户Id获取角色列表
     * @Author LiYuan
     * @Description
     * @Date 14:51 2020/12/24
     * @param userId
     * @return java.util.List<com.nexus.pojo.UmsRole>
    **/
    List<UmsRole> getRoleList(@Param("userId") Long userId);
    /**
     *获取资源相关用户ID列表
     * @Author LiYuan
     * @Description //TODO
     * @Date 14:53 2020/12/24
     * @param resourceId
     * @return java.util.List<java.lang.Long>
    **/
    List<Long> getUserIdList(@Param("resourceId") Long resourceId);
}

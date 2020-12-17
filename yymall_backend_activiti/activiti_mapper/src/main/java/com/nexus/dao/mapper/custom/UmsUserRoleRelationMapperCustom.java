package com.nexus.dao.mapper.custom;

import com.nexus.pojo.UmsResource;
import com.nexus.pojo.UmsRole;
import com.nexus.pojo.UmsUserRoleRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UmsUserRoleRelationMapperCustom {
    int insertList(@Param("list") List<UmsUserRoleRelation> adminRoleRelationList);
    /**
     * 获取用户所有可访问资源
     * @Author : Nexus
     * @Description :
     * @Date : 2020/9/10 22:42
     * @Param : adminId
     * @return : java.util.List<com.nexus.mall.pojo.BackendResource>
     **/
    List<UmsResource> getResourceList(@Param("userId") Long userId);
    List<UmsRole> getRoleList(@Param("userId") Long userId);
    List<Long> getUserIdList(@Param("resourceId") Long resourceId);
}

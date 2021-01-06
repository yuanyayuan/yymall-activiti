package com.nexus.service;

import com.nexus.pojo.UmsResource;
import com.nexus.pojo.UmsRole;
import com.nexus.pojo.UmsUser;
import com.nexus.pojo.bo.user.UpdateUserPasswordBO;
import com.nexus.pojo.bo.user.UserCreateBO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
/**
 * @className IUmsUserService
 * @description 用户管理Service
 * @author LiYuan
 * @date 2020/12/14
**/
public interface IUmsUserService {
    /**
     *判断用户名是否唯一
     * @Author LiYuan
     * @Description 判断用户名是否唯一
     * @Date 9:26 2020/12/14
     * @param username 用户名
     * @return boolean
    **/
    boolean queryUsernameIsExist(String username);
    /**
     * 注册功能
     * @Author LiYuan
     * @Description 注册功能
     * @Date 9:26 2020/12/14
     * @param userCreateBO
     * @return com.nexus.pojo.UmsUser
    **/
    UmsUser register(UserCreateBO userCreateBO);
    /**
     * 登录功能
     * @Author LiYuan
     * @Description 登录功能
     * @Date 9:26 2020/12/14
     * @param username
     * @param password
     * @return java.lang.String
    **/
    String login(String username,String password);

    /**
     * 根据用户名获取后台管理员
     * @Author LiYuan
     * @Description 根据用户名获取后台管理员
     * @Date 9:15 2020/12/14
     * @param username 用户名
     * @return com.nexus.pojo.UmsUser
    **/
    UmsUser getUserByUsername(String username);
    /**
     * SpringSecurity读取用户信息及权限
     * @Author LiYuan
     * @Description
     * @Date 9:21 2020/12/14
     * @param username 用户名
     * @return org.springframework.security.core.userdetails.UserDetails
    **/
    UserDetails loadUserByUsername(String username);
    /**
     * 根据用户Id获取用户资源
     * @Author LiYuan
     * @Description
     * @Date 9:22 2020/12/14
     * @param userId 用户Id
     * @return java.util.List<com.nexus.pojo.UmsResource>
    **/
    List<UmsResource> getResourceList(Long userId);
    /**
     * 获取用户的角色
     * @Author LiYuan
     * @Description
     * @Date 15:06 2020/12/24
     * @param userId
     * @return java.util.List<com.nexus.pojo.UmsRole>
    **/
    List<UmsRole> getRoleList(Long userId);

    /**
     * 刷新token的功能
     * @Author LiYuan
     * @Description
     * @Date 14:12 2020/12/24
     * @param oldToken
     * @return java.lang.String
    **/
    String refreshToken(String oldToken);
    /**
     * 根据用户名或昵称分页查询用户
     * @Author LiYuan
     * @Description
     * @Date 14:57 2020/12/24
     * @param keyword
     * @param page
     * @param pageSize
     * @return java.util.List<com.nexus.pojo.UmsUser>
    **/
    List<UmsUser> list(String keyword, Integer page, Integer pageSize);

    /**
     *
     * 根据用户id获取用户
     *
     * @Author LiYuan
     * @Description
     * @Date 15:04 2020/12/24
     * @param id
     * @return com.nexus.pojo.UmsUser
    **/
    UmsUser getItem(Long id);
    /**
     * 修改指定用户信息
     * @Author LiYuan
     * @Description
     * @Date 15:10 2020/12/24
     * @param id
     * @param user
     * @return int
    **/
    int update(Long id, UmsUser user);
    /**
     *
     * @Author LiYuan
     * @Description
     * @Date 11:09 2020/12/25
     * @param param
     * @return int
    **/
    int updatePassword(UpdateUserPasswordBO param);


    /**
     *
     * 删除指定用户
     *
     * @Author LiYuan
     * @Description 删除指定用户
     * @Date 10:34 2020/11/2
     * @param id
     * @return int
     **/
    int delete(Long id);

    /**
     *
     * 修改用户角色关系
     *
     * @Author LiYuan
     * @Description 修改用户角色关系
     * @Date 10:32 2020/11/5
     * @param adminId
     * @param roleIds
     * @return int
     **/
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = RuntimeException.class)
    int updateRole(Long adminId, List<Long> roleIds);


    List<HashMap<String,Object>> getUserList();
}

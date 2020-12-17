package com.nexus.service;

import com.nexus.pojo.UmsResource;
import com.nexus.pojo.UmsUser;
import com.nexus.pojo.bo.user.UserCreateBO;
import org.springframework.security.core.userdetails.UserDetails;

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
     * @Description //TODO
     * @Date 9:21 2020/12/14
     * @param username 用户名
     * @return org.springframework.security.core.userdetails.UserDetails
    **/
    UserDetails loadUserByUsername(String username);
    /**
     * 根据用户Id获取用户资源
     * @Author LiYuan
     * @Description //TODO
     * @Date 9:22 2020/12/14
     * @param userId 用户Id
     * @return java.util.List<com.nexus.pojo.UmsResource>
    **/
    List<UmsResource> getResourceList(Long userId);
}

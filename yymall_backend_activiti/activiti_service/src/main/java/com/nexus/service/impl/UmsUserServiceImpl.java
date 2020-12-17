package com.nexus.service.impl;

import com.nexus.common.exception.Asserts;
import com.nexus.dao.mapper.UmsUserMapper;
import com.nexus.dao.mapper.custom.UmsUserRoleRelationMapperCustom;
import com.nexus.pojo.UmsResource;
import com.nexus.pojo.UmsUser;
import com.nexus.pojo.bo.user.UserCreateBO;
import com.nexus.pojo.bo.user.UserDetailsBO;
import com.nexus.security.util.JwtTokenUtil;
import com.nexus.service.IUmsUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class UmsUserServiceImpl implements IUmsUserService {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UmsUserMapper userMapper;
    @Autowired
    private UmsUserRoleRelationMapperCustom userRoleRelationMapperCustom;

    @Override
    public UmsUser getUserByUsername(String username) {
        Example example = new Example(UmsUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username",username);
        List<UmsUser> adminList = userMapper.selectByExample(example);
        if (adminList != null && adminList.size() > 0) {
            return adminList.get(0);
        }
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        UmsUser user = getUserByUsername(username);
        if (user != null) {
            List<UmsResource> resourceList = getResourceList(user.getId());
            return new UserDetailsBO(user, resourceList);
        }
        throw  new UsernameNotFoundException("用户名或密码错误");
    }

    @Override
    public List<UmsResource> getResourceList(Long userId) {
        return userRoleRelationMapperCustom.getResourceList(userId);
    }

    /**
     * 判断用户名是否唯一
     *
     * @param username 用户名
     * @return boolean
     * @Author LiYuan
     * @Description 判断用户名是否唯一
     * @Date 9:26 2020/12/14
     **/
    @Override
    public boolean queryUsernameIsExist(String username) {
        Example example = new Example(UmsUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username",username);
        UmsUser result = userMapper.selectOneByExample(example);
        return result != null;
    }

    /**
     * 注册功能
     *
     * @param userParam
     * @return com.nexus.pojo.UmsUser
     * @Author LiYuan
     * @Description 注册功能
     * @Date 9:26 2020/12/14
     **/
    @Override
    public UmsUser register(UserCreateBO userParam) {
        UmsUser user = new UmsUser();
        BeanUtils.copyProperties(userParam,user);
        user.setCreateTime(new Date());
        //查询是否有相同用户名的用户
        if(queryUsernameIsExist(user.getUsername())){
            return null;
        }
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        userMapper.insertSelective(user);
        return user;
    }

    /**
     * 登录功能
     *
     * @param username
     * @param password
     * @return java.lang.String
     * @Author LiYuan
     * @Description 登录功能
     * @Date 9:26 2020/12/14
     **/
    @Override
    public String login(String username, String password) {
        String token = null;
        //密码需要客户端加密后传递
        try {
            UserDetails userDetails = loadUserByUsername(username);
            if(!passwordEncoder.matches(password,userDetails.getPassword())){
                Asserts.fail("密码不正确");
            }
            if(!userDetails.isEnabled()){
                Asserts.fail("帐号已被禁用");
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenUtil.generateToken(userDetails);
        }catch (AuthenticationException e){
            log.warn("登录异常:{}", e.getMessage());
        }
        return token;
    }
}

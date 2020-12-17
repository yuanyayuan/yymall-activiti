package com.nexus.api.controller;

import com.google.common.collect.Maps;
import com.nexus.common.api.ResultCode;
import com.nexus.common.api.ServerResponse;
import com.nexus.pojo.UmsUser;
import com.nexus.pojo.bo.user.UserCreateBO;
import com.nexus.pojo.bo.user.UserLoginBO;
import com.nexus.service.IUmsResourceService;
import com.nexus.service.IUmsUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.Map;

@Api(value = "后台用户相关接口",tags = {"用于后台用户的相关接口"})
@Validated
@RestController
@RequestMapping("/user")
public class UserController {
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    /**
     * Authorization
     **/
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    private IUmsUserService adminService;

    @ApiOperation(value = "用户名唯一校验", notes = "用户名唯一校验", httpMethod = "GET")
    @GetMapping("/usernameIsExist")
    public ServerResponse usernameIsExist(@NotBlank(message = "用户名不能为空") @RequestParam("username") String username){
        //查找注册的用户名是否存在
        boolean usernameIsExist = adminService.queryUsernameIsExist(username);
        if(usernameIsExist){
            return ServerResponse.failed(ResultCode.REGISTER_DUP_FAIL);
        }
        //请求成功，用户名没有重复
        return ServerResponse.success(null,"该用户不存在");
    }

    @ApiOperation(value = "用户注册", notes = "用户注册", httpMethod = "POST")
    @PostMapping("/register")
    public ServerResponse<UmsUser> register(@Validated @RequestBody UserCreateBO user){
        UmsUser register = adminService.register(user);
        if(register == null){
            return ServerResponse.failed();
        }
        return ServerResponse.success(register);
    }


    @ApiOperation(value = "登录以后返回token",notes = "用户登录", httpMethod = "POST")
    @PostMapping(value = "/login")
    public ServerResponse login(@Validated @RequestBody UserLoginBO userLoginParam){
        String token = adminService.login(userLoginParam.getUsername(), userLoginParam.getPassword());
        if(token == null){
            return ServerResponse.validateFailed("用户名或密码错误");
        }
        Map<String, String> tokenMap = Maps.newHashMap();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return ServerResponse.success(tokenMap);
    }
}

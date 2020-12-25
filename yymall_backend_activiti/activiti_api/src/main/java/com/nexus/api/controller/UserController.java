package com.nexus.api.controller;

import cn.hutool.core.collection.CollUtil;
import com.google.common.collect.Maps;
import com.nexus.common.api.CommonPage;
import com.nexus.common.api.ResultCode;
import com.nexus.common.api.ServerResponse;
import com.nexus.pojo.UmsRole;
import com.nexus.pojo.UmsUser;
import com.nexus.pojo.bo.user.UserCreateBO;
import com.nexus.pojo.bo.user.UserLoginBO;
import com.nexus.service.IUmsResourceService;
import com.nexus.service.IUmsRoleService;
import com.nexus.service.IUmsUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    private IUmsUserService userService;
    @Autowired
    private IUmsRoleService roleService;

    @ApiOperation(value = "用户名唯一校验", notes = "用户名唯一校验", httpMethod = "GET")
    @GetMapping("/usernameIsExist")
    public ServerResponse usernameIsExist(@NotBlank(message = "用户名不能为空") @RequestParam("username") String username){
        //查找注册的用户名是否存在
        boolean usernameIsExist = userService.queryUsernameIsExist(username);
        if(usernameIsExist){
            return ServerResponse.failed(ResultCode.REGISTER_DUP_FAIL);
        }
        //请求成功，用户名没有重复
        return ServerResponse.success(null,"该用户不存在");
    }

    @ApiOperation(value = "用户注册", notes = "用户注册", httpMethod = "POST")
    @PostMapping("/register")
    public ServerResponse<UmsUser> register(@Validated @RequestBody UserCreateBO user){
        UmsUser register = userService.register(user);
        if(register == null){
            return ServerResponse.failed();
        }
        return ServerResponse.success(register);
    }


    @ApiOperation(value = "登录以后返回token",notes = "用户登录", httpMethod = "POST")
    @PostMapping(value = "/login")
    public ServerResponse login(@Validated @RequestBody UserLoginBO userLoginParam){
        String token = userService.login(userLoginParam.getUsername(), userLoginParam.getPassword());
        if(token == null){
            return ServerResponse.validateFailed("用户名或密码错误");
        }
        Map<String, String> tokenMap = Maps.newHashMap();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return ServerResponse.success(tokenMap);
    }

    @ApiOperation(value = "刷新token",notes = "刷新token", httpMethod = "GET")
    @GetMapping(value = "/refreshToken")
    public ServerResponse refreshToken(HttpServletRequest request){
        String token = request.getHeader(tokenHeader);
        String newToken = userService.refreshToken(token);
        if(newToken == null){
            return ServerResponse.failed("Token已过期");
        }
        Map<String, String> tokenMap = Maps.newHashMap();
        tokenMap.put("token", newToken);
        tokenMap.put("tokenHead", tokenHead);
        return ServerResponse.success(tokenMap);
    }

    @ApiOperation(value = "获取当前登录用户信息",notes = "获取当前登录用户信息", httpMethod = "GET")
    @GetMapping(value = "/info")
    public ServerResponse getAdminInfo(Principal principal){
        if(principal == null){
            return ServerResponse.unauthorized(null);
        }
        String username = principal.getName();
        UmsUser admin = userService.getUserByUsername(username);
        Map<String, Object> data = Maps.newHashMap();
        data.put("username",admin.getUsername());
        data.put("menus",roleService.getMenuList(admin.getId()));
        data.put("icon",admin.getIcon());
        List<UmsRole> roleList = userService.getRoleList(admin.getId());
        if(CollUtil.isNotEmpty(roleList)){
            List<String> roles = roleList.stream().map(UmsRole::getName).collect(Collectors.toList());
            data.put("roles",roles);
        }
        return ServerResponse.success(data);
    }

    @ApiOperation(value = "登出功能",notes = "登出功能", httpMethod = "POST")
    @PostMapping(value = "/logout")
    public ServerResponse logout() {
        return ServerResponse.success(null);
    }

    @ApiOperation(value = "根据用户名或姓名分页获取用户列表",notes = "根据用户名或姓名分页获取用户列表", httpMethod = "GET")
    @GetMapping(value = "/list")
    public ServerResponse<CommonPage<UmsUser>> list(
            @ApiParam(name = "keyword", value = "关键字", required = true)
            @RequestParam(value = "keyword", required = false)
                    String keyword,
            @ApiParam(name = "page", value = "查询下一页的第几页", required = false)
            @RequestParam(value = "page", defaultValue = "1")
                    Integer page,
            @ApiParam(name = "pageSize", value = "分页的每一页显示的条数", required = false)
            @RequestParam(value = "pageSize", defaultValue = "5")
                    Integer pageSize){
        List<UmsUser> adminList = userService.list(keyword, page, pageSize);
        return ServerResponse.success(CommonPage.restPage(adminList));
    }

    @ApiOperation(value = "获取指定用户信息",notes = "获取指定用户信息",httpMethod = "GET")
    @GetMapping(value = "/{id}")
    public ServerResponse<UmsUser> getItem(@PathVariable Long id) {
        UmsUser user = userService.getItem(id);
        return ServerResponse.success(user);
    }

    @ApiOperation(value = "修改指定用户信息",notes = "修改指定用户信息",httpMethod = "POST")
    @PostMapping(value = "/update/{id}")
    public ServerResponse update(@PathVariable Long id, @RequestBody UmsUser user) {
        int count = userService.update(id, user);
        if (count > 0) {
            return ServerResponse.success(count);
        }
        return ServerResponse.failed();
    }


}

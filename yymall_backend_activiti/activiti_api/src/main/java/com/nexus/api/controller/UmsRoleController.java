package com.nexus.api.controller;

import com.nexus.common.api.CommonPage;
import com.nexus.common.api.ServerResponse;
import com.nexus.pojo.UmsMenu;
import com.nexus.pojo.UmsResource;
import com.nexus.pojo.UmsRole;
import com.nexus.pojo.UmsUser;
import com.nexus.service.IUmsRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @className RoleController
 * @description 角色管理
 * @author LiYuan
 * @date 2020/11/5
**/
@Api(value = "角色管理相关接口",tags = {"角色管理相关接口"})
@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/role")
public class UmsRoleController{

    private final IUmsRoleService umsRoleService;

    @ApiOperation(value = "添加角色", notes = "添加角色", httpMethod = "POST")
    @PostMapping(value = "/create")
    public ServerResponse create(@RequestBody UmsRole role) {
        int count = umsRoleService.create(role);
        if (count > 0) {
            return ServerResponse.success(count);
        }
        return ServerResponse.failed();
    }

    @ApiOperation(value = "修改角色", notes = "添加角色", httpMethod = "POST")
    @PostMapping(value = "/update/{id}")
    public ServerResponse update(@PathVariable Long id, @RequestBody UmsRole role) {
        int count = umsRoleService.update(id, role);
        if (count > 0) {
            return ServerResponse.success(count);
        }
        return ServerResponse.failed();
    }

    @ApiOperation(value = "批量删除角色", notes = "批量删除角色", httpMethod = "POST")
    @PostMapping(value = "/delete")
    public ServerResponse delete(@RequestParam("ids") List<Long> ids) {
        int count = umsRoleService.delete(ids);
        if (count > 0) {
            return ServerResponse.success(count);
        }
        return ServerResponse.failed();
    }

    @ApiOperation(value = "获取所有角色", notes = "获取所有角色", httpMethod = "GET")
    @GetMapping(value = "/listAll")
    public ServerResponse<List<UmsRole>> listAll() {
        List<UmsRole> roleVOList = umsRoleService.list();
        return ServerResponse.success(roleVOList);
    }

    @ApiOperation(value = "根据角色名称分页获取角色列表", notes = "根据角色名称分页获取角色列表", httpMethod = "GET")
    @GetMapping(value = "/list")
    public ServerResponse<CommonPage<UmsRole>> list(@RequestParam(value = "keyword", required = false)
                                           String keyword,
                                                    @RequestParam(value = "pageSize", defaultValue = "10")
                                       Integer pageSize,
                                                    @RequestParam(value = "page", defaultValue = "1")
                                           Integer page) {
        List<UmsRole> list = umsRoleService.list(keyword, pageSize, page);
        return ServerResponse.success(CommonPage.restPage(list));
    }

    @ApiOperation(value = "修改角色状态", notes = "修改角色状态", httpMethod = "POST")
    @PostMapping(value = "/updateStatus/{id}")
    public ServerResponse updateStatus(@PathVariable Long id, @RequestParam(value = "status") Integer status) {
        UmsRole umsRole = new UmsRole();
        umsRole.setStatus(status);
        int count = umsRoleService.update(id, umsRole);
        if (count > 0) {
            return ServerResponse.success(count);
        }
        return ServerResponse.failed();
    }

    @ApiOperation(value = "获取角色相关菜单", notes = "获取角色相关菜单", httpMethod = "GET")
    @GetMapping(value = "/listMenu/{roleId}")
    public ServerResponse<List<UmsMenu>> listMenu(@PathVariable Long roleId) {
        List<UmsMenu> roleList = umsRoleService.listMenu(roleId);
        return ServerResponse.success(roleList);
    }

    @ApiOperation(value = "获取角色相关资源", notes = "获取角色相关资源", httpMethod = "GET")
    @GetMapping(value = "/listResource/{roleId}")
    public ServerResponse<List<UmsResource>> listResource(@PathVariable Long roleId) {
        List<UmsResource> roleList = umsRoleService.listResource(roleId);
        return ServerResponse.success(roleList);
    }

    @ApiOperation(value = "给角色分配菜单", notes = "给角色分配菜单", httpMethod = "GET")
    @GetMapping(value = "/allocMenu")
    @ResponseBody
    public ServerResponse allocMenu(@RequestParam Long roleId, @RequestParam List<Long> menuIds) {
        int count = umsRoleService.allocMenu(roleId, menuIds);
        return ServerResponse.success(count);
    }

    @ApiOperation(value = "给角色分配资源",notes = "给角色分配资源",httpMethod = "POST")
    @PostMapping(value = "/allocResource")
    public ServerResponse allocResource(@RequestParam Long roleId, @RequestParam List<Long> resourceIds) {
        int count = umsRoleService.allocResource(roleId, resourceIds);
        return ServerResponse.success(count);
    }

}

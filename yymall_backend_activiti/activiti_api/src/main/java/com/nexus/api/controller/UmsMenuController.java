package com.nexus.api.controller;


import com.nexus.common.api.CommonPage;
import com.nexus.common.api.ServerResponse;
import com.nexus.pojo.UmsMenu;
import com.nexus.pojo.dto.user.UmsMenuNode;
import com.nexus.service.IUmsMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @className UmsMenuController
 * @description 后台菜单管理
 * @author LiYuan
 * @date 2020/12/7
**/
@Api(value = "后台菜单管理",tags = {"后台菜单管理"})
@Validated
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/menu")
public class UmsMenuController {

    private final IUmsMenuService menuService;

    @ApiOperation(value = "添加后台菜单", notes = "添加后台菜单", httpMethod = "POST")
    @PostMapping(value = "/create")
    public ServerResponse create(@RequestBody UmsMenu menu){
        int count = menuService.create(menu);
        if (count > 0){
            return ServerResponse.success(count);
        }else {
            return ServerResponse.failed();
        }
    }

    @ApiOperation(value = "修改后台菜单",notes = "添加后台菜单", httpMethod = "POST")
    @PostMapping(value = "/update/{id}")
    public ServerResponse update(@PathVariable Long id,
                                 @RequestBody UmsMenu menu) {
        int count = menuService.update(id, menu);
        if (count > 0) {
            return ServerResponse.success(count);
        } else {
            return ServerResponse.failed();
        }
    }

    @ApiOperation(value = "根据ID获取菜单详情",notes = "根据ID获取菜单详情", httpMethod = "GET")
    @GetMapping(value = "/{id}")
    public ServerResponse<UmsMenu> getItem(@PathVariable Long id) {
        UmsMenu menu = menuService.getItem(id);
        return ServerResponse.success(menu);
    }

    @ApiOperation("根据ID删除后台菜单")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse delete(@PathVariable Long id) {
        int count = menuService.delete(id);
        if (count > 0) {
            return ServerResponse.success(count);
        } else {
            return ServerResponse.failed();
        }
    }

    @ApiOperation("分页查询后台菜单")
    @RequestMapping(value = "/list/{parentId}", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<CommonPage<UmsMenu>> list(
            @ApiParam(name = "parentId", value = "父级id", required = true)
            @PathVariable
                    Long parentId,
            @ApiParam(name = "page", value = "查询下一页的第几页", required = false)
            @RequestParam(value = "page", defaultValue = "1")
                    Integer page,
            @ApiParam(name = "pageSize", value = "分页的每一页显示的条数", required = false)
            @RequestParam(value = "pageSize", defaultValue = "10")
                    Integer pageSize) {
        List<UmsMenu> menuList = menuService.list(parentId, page, pageSize);
        return ServerResponse.success(CommonPage.restPage(menuList));
    }

    @ApiOperation("树形结构返回所有菜单列表")
    @RequestMapping(value = "/treeList", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<List<UmsMenuNode>> treeList() {
        List<UmsMenuNode> list = menuService.treeList();
        return ServerResponse.success(list);
    }

    @ApiOperation("修改菜单显示状态")
    @RequestMapping(value = "/updateHidden/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse updateHidden(@PathVariable Long id, @RequestParam("hidden") Integer hidden) {
        int count = menuService.updateHidden(id, hidden);
        if (count > 0) {
            return ServerResponse.success(count);
        } else {
            return ServerResponse.failed();
        }
    }
}

package com.nexus.api.controller;

import com.nexus.common.api.ServerResponse;
import com.nexus.pojo.UmsResourceCategory;
import com.nexus.service.IUmsResourceCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @className UmsResourceCategoryController
 * @description
 * @author LiYuan
 * @date 2020/12/7
**/
@Api(value = "后台资源分类管理",tags = {"后台资源分类管理"})
@Validated
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/resourceCategory")
public class UmsResourceCategoryController {

    private final IUmsResourceCategoryService resourceCategoryService;

    @ApiOperation(value = "查询所有后台资源分类", notes = "查询所有后台资源分类", httpMethod = "GET")
    @GetMapping(value = "/listAll")
    public ServerResponse<List<UmsResourceCategory>> listAll() {
        List<UmsResourceCategory> resourceList = resourceCategoryService.listAll();
        return ServerResponse.success(resourceList);
    }
    @ApiOperation(value = "添加后台资源分类", notes = "添加后台资源分类", httpMethod = "POST")
    @PostMapping(value = "/create")
    public ServerResponse create(@RequestBody UmsResourceCategory umsResourceCategory) {
        int count = resourceCategoryService.create(umsResourceCategory);
        if (count > 0) {
            return ServerResponse.success(count);
        } else {
            return ServerResponse.failed();
        }
    }
    @ApiOperation(value = "修改后台资源分类", notes = "修改后台资源分类", httpMethod = "POST")
    @PostMapping(value = "/update/{id}")
    public ServerResponse update(@PathVariable Long id,
                               @RequestBody UmsResourceCategory umsResourceCategory) {
        int count = resourceCategoryService.update(id, umsResourceCategory);
        if (count > 0) {
            return ServerResponse.success(count);
        } else {
            return ServerResponse.failed();
        }
    }
    @ApiOperation(value = "根据ID删除后台资源", notes = "根据ID删除后台资源", httpMethod = "POST")
    @PostMapping(value = "/delete/{id}")
    public ServerResponse delete(@PathVariable Long id) {
        int count = resourceCategoryService.delete(id);
        if (count > 0) {
            return ServerResponse.success(count);
        } else {
            return ServerResponse.failed();
        }
    }
}

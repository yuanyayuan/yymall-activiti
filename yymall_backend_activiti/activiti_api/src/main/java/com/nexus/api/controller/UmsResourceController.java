package com.nexus.api.controller;


import com.nexus.common.api.CommonPage;
import com.nexus.common.api.ServerResponse;
import com.nexus.pojo.UmsResource;
import com.nexus.security.component.DynamicSecurityMetadataSource;
import com.nexus.service.IUmsResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**

* @Description:    java类作用描述 

* @Author:         Nexus

* @CreateDate:     2021/1/9 19:48

* @UpdateUser:     Nexus 

* @UpdateDate:     2021/1/9 19:48

* @UpdateRemark:   修改内容

* @Version:        1.0

*/
@Api(value = "后台资源管理",tags = {"后台资源管理"})
@Validated
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/resource")
public class UmsResourceController {
    private final IUmsResourceService resourceService;

    private final DynamicSecurityMetadataSource dynamicSecurityMetadataSource;

    /**
     * 添加后台资源
     * @Author : Nexus
     * @Description : 
     * @Date : 2021/1/9 19:47
     * @Param : umsResource
     * @return : com.nexus.common.api.ServerResponse
     **/
    @ApiOperation(value = "添加后台资源", notes = "添加后台资源", httpMethod = "GET")
    @GetMapping(value = "/create")
    public ServerResponse create(@RequestBody UmsResource umsResource) {
        int count = resourceService.create(umsResource);
        dynamicSecurityMetadataSource.clearDataSource();
        if (count > 0) {
            return ServerResponse.success(count);
        } else {
            return ServerResponse.failed();
        }
    }

    @ApiOperation(value = "修改后台资源", notes = "修改后台资源", httpMethod = "POST")
    @PostMapping(value = "/update/{id}")
    public ServerResponse update(@PathVariable Long id,
                                 @RequestBody UmsResource resource){
        int count = resourceService.update(id, resource);
        dynamicSecurityMetadataSource.clearDataSource();
        if (count > 0){
            return ServerResponse.success(count);
        }else {
            return ServerResponse.failed();
        }
    }

    @ApiOperation(value = "根据ID获取资源详情", notes = "根据ID获取资源详情", httpMethod = "POST")
    @PostMapping(value = "/{id}")
    public ServerResponse<UmsResource> getItem(@PathVariable Long id){
        UmsResource backendResource = resourceService.getItem(id);
        return ServerResponse.success(backendResource);
    }

    @ApiOperation(value = "根据ID删除后台资源",notes = "根据ID删除后台资源",httpMethod = "POST")
    @PostMapping(value = "/delete/{id}")
    public ServerResponse delete(@PathVariable Long id) {
        int count = resourceService.delete(id);
        dynamicSecurityMetadataSource.clearDataSource();
        if (count > 0) {
            return ServerResponse.success(count);
        } else {
            return ServerResponse.failed();
        }
    }
    @ApiOperation(value = "分页模糊查询后台资源",notes = "分页模糊查询后台资源",httpMethod = "GET")
    @GetMapping(value = "/list")
    public ServerResponse<CommonPage<UmsResource>> list(
            @RequestParam(required = false)
                    Long categoryId,
            @RequestParam(required = false)
                    String nameKeyword,
            @RequestParam(required = false)
                    String urlKeyword,
            @RequestParam(value = "page", defaultValue = "1")
                    Integer page,
            @RequestParam(value = "pageSize", defaultValue = "10")
                    Integer pageSize) {
        List<UmsResource> resourceList = resourceService.list(
                categoryId,
                nameKeyword,
                urlKeyword,
                page,
                pageSize);
        return ServerResponse.success(CommonPage.restPage(resourceList));
    }

    @ApiOperation(value = "查询所有后台资源",notes = "查询所有后台资源",httpMethod = "GET")
    @GetMapping(value = "/listAll")
    public ServerResponse<List<UmsResource>> listAll() {
        List<UmsResource> resourceList = resourceService.listAll();
        return ServerResponse.success(resourceList);
    }
}

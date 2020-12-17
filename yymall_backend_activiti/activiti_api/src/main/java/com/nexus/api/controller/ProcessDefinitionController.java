package com.nexus.api.controller;

import com.google.common.collect.Lists;
import com.nexus.common.api.ResultCode;
import com.nexus.common.api.ServerResponse;
import com.nexus.common.exception.Asserts;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**

* @Description:    流程定义Controller

* @Author:         Nexus

* @CreateDate:     2020/12/17 21:51

* @UpdateUser:     Nexus 

* @UpdateDate:     2020/12/17 21:51

* @UpdateRemark:   修改内容

* @Version:        1.0

*/
@Api(value = "流程定义相关接口",tags = {"流程定义相关接口"})
@Validated
@RestController
@RequestMapping("/processDefinition")
@Slf4j
public class ProcessDefinitionController {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private ProcessRuntime processRuntime;

    //添加流程定义通过上传BPMN
    //添加流程定义通过在线提交BPMN的xml
    /**
     * 获取流程定义列表
     * @Author : Nexus
     * @Description : 获取流程定义列表
     * @Date : 2020/12/17 21:57
     * @Param :
     * @return : com.nexus.common.api.ServerResponse
     **/
    @ApiOperation(value = "获取流程定义列表", notes = "获取流程定义列表", httpMethod = "GET")
    @GetMapping("/getDefinitions")
    public ServerResponse<List<HashMap<String, Object>>> getDefinitions(){
        try {
            List<HashMap<String, Object>> listMap= Lists.newArrayList();
            List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().list();
            list.sort((y,x)->x.getVersion()-y.getVersion());
            for (ProcessDefinition pd : list) {
                HashMap<String, Object> hashMap = new HashMap<>();
                ///System.out.println("流程定义ID："+pd.getId());
                hashMap.put("processDefinitionID", pd.getId());
                hashMap.put("name", pd.getName());
                hashMap.put("key", pd.getKey());
                hashMap.put("resourceName", pd.getResourceName());
                hashMap.put("deploymentID", pd.getDeploymentId());
                hashMap.put("version", pd.getVersion());
                listMap.add(hashMap);
            }
            return ServerResponse.success(listMap);
        }catch(Exception e) {
            Asserts.fail(ResultCode.PROCESSDEFINITION_GET_ERROR);
            log.error("[ProcessDefinitionController][getDefinitions]发生错误,错误信息:{}",e.getMessage());
        }
        return null;
    }
    //获取流程定义XML
    //获取流程部署列表
    //删除流程定义
}

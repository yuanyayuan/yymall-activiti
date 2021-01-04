package com.nexus.api.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.nexus.common.api.ResultCode;
import com.nexus.common.api.ServerResponse;
import com.nexus.pojo.bo.user.UserDetailsBO;
import com.nexus.security.util.SecurityUtil;
import com.nexus.service.IUmsUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.activiti.api.model.shared.model.VariableInstance;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.builders.ProcessPayloadBuilder;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**

* @Description:    java类作用描述

* @Author:         Nexus

* @CreateDate:     2020/12/20 18:22

* @UpdateUser:     Nexus

* @UpdateDate:     2020/12/20 18:22

* @UpdateRemark:   修改内容

* @Version:        1.0

*/
@Api(value = "流程实例相关接口",tags = {"流程实例相关接口"})
@Validated
@RestController
@RequestMapping("/processInstance")
@Slf4j
public class ProcessInstanceController {
    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private ProcessRuntime processRuntime;

    /**
     * 查询流程实例
     * @Author : Nexus
     * @Description : Principal principal 获取当前登录用户
     * @Date : 2020/12/20 18:34
     * @Param :
     * @return : com.nexus.common.api.ServerResponse<java.util.List<java.util.HashMap<java.lang.String,java.lang.Object>>>
     **/
    @ApiOperation(value = "查询流程实例", notes = "查询流程实例", httpMethod = "GET")
    @GetMapping("/getInstances")
    public ServerResponse<List<HashMap<String, Object>>> getInstances(@AuthenticationPrincipal UserDetailsBO user){
        Page<ProcessInstance> processInstances = null;
        try {
            processInstances=processRuntime.processInstances(Pageable.of(0,  100));
            List<ProcessInstance> list = processInstances.getContent();
            list.sort((y,x)->x.getStartDate().toString().compareTo(y.getStartDate().toString()));
            List<HashMap<String, Object>> listMap = Lists.newArrayList();

            for(ProcessInstance pi : list){
                HashMap<String, Object> hashMap = Maps.newHashMap();
                hashMap.put("id", pi.getId());
                hashMap.put("name", pi.getName());
                hashMap.put("status", pi.getStatus());
                hashMap.put("processDefinitionId", pi.getProcessDefinitionId());
                hashMap.put("processDefinitionKey", pi.getProcessDefinitionKey());
                hashMap.put("startDate", pi.getStartDate());
                hashMap.put("processDefinitionVersion", pi.getProcessDefinitionVersion());
                //因为processRuntime.processDefinition("流程部署ID")查询的结果没有部署流程与部署ID，所以用repositoryService查询
                //todo processDefinition和processInstances联表查询
                ProcessDefinition pd = repositoryService.createProcessDefinitionQuery()
                        .processDefinitionId(pi.getProcessDefinitionId())
                        .singleResult();
                hashMap.put("resourceName", pd.getResourceName());
                hashMap.put("deploymentId", pd.getDeploymentId());
                listMap.add(hashMap);
            }
            return ServerResponse.success(listMap);
        }catch(Exception e) {
            log.error("[ProcessInstanceController][getInstances]发生错误,错误信息:{}",e.getMessage());
            return ServerResponse.failed(ResultCode.PROCESSINSTANCE_GET_ERROR);
        }
    }
    /**
     * 启动流程实例
     * @Author : Nexus
     * @Description :
     * @Date : 2020/12/20 19:03
     * @Param : processDefinitionKey
     * @Param : instanceName
     * @Param : instanceVariable
     * @return : com.nexus.common.api.ServerResponse
     **/
    @ApiOperation(value = "启动流程实例", notes = "启动流程实例", httpMethod = "GET")
    @GetMapping("/startProcess")
    public ServerResponse startProcess(
            @RequestParam("processDefinitionKey") String processDefinitionKey,
            @RequestParam("instanceName") String instanceName,
            @RequestParam(value = "instanceVariable",required = false) String instanceVariable) {
        try {
            ProcessInstance processInstance = processRuntime.start(ProcessPayloadBuilder
                    .start()
                    .withProcessDefinitionKey(processDefinitionKey)
                    .withName(instanceName)
                    //.withVariable("content", instanceVariable)
                    //.withVariable("参数2", "参数2的值")
                    //.withBusinessKey("自定义BusinessKey")
                    .build());
            return ServerResponse.success(processInstance.getName()+"；"+processInstance.getId());
        }catch (Exception e){
            log.error("[ProcessInstanceController][startProcess]发生错误,错误信息:{}",e.getMessage());
            return ServerResponse.failed(ResultCode.PROCESSINSTANCE_CREATE_ERROR);
        }
    }
    /**
     * 删除流程实例
     * @Author : Nexus
     * @Description :
     * @Date : 2020/12/20 23:00
     * @Param : instanceID 流程实例ID （ProcessInstance的Id）
     * @return : com.nexus.common.api.ServerResponse
     **/
    @ApiOperation(value = "删除流程实例", notes = "删除流程实例", httpMethod = "GET")
    @GetMapping("/deleteInstance")
    public ServerResponse deleteInstance(@RequestParam("instanceID") String instanceID) {
        try {
            ProcessInstance processInstance = processRuntime.delete(ProcessPayloadBuilder
                    .delete()
                    .withProcessInstanceId(instanceID)
                    .build()
            );
            return ServerResponse.success(processInstance.getName());
        }
        catch(Exception e)
        {
            return ServerResponse.failed(ResultCode.POCESSINSTANCE_DELETE_ERROR);
        }
    }
    /**
     * 挂起冷冻
     * @Author : Nexus
     * @Description :
     * @Date : 2020/12/20 22:22
     * @Param : instanceID 流程实例ID （ProcessInstance的Id）
     * @return : com.nexus.common.api.ServerResponse
     **/
    @ApiOperation(value = "挂起流程实例", notes = "挂起流程实例", httpMethod = "GET")
    @GetMapping("/suspendInstance")
    public ServerResponse suspendInstance(@RequestParam("instanceID") String instanceID) {
        try {
            ProcessInstance processInstance = processRuntime.suspend(ProcessPayloadBuilder
                    .suspend()
                    .withProcessInstanceId(instanceID)
                    .build()
            );
            return ServerResponse.success(processInstance.getName());
        }
        catch(Exception e)
        {
            return ServerResponse.failed(ResultCode.POCESSINSTANCE_SUSPEND_ERROR);
        }
    }
    /**
     * 激活流程实例
     * @Author : Nexus
     * @Description :
     * @Date : 2020/12/20 23:05
     * @Param : instanceID 流程实例ID （ProcessInstance的Id）
     * @return : com.nexus.common.api.ServerResponse
     **/
    @ApiOperation(value = "激活流程实例", notes = "激活流程实例", httpMethod = "GET")
    @GetMapping("/resumeInstance")
    public ServerResponse resumeInstance(@RequestParam("instanceID") String instanceID) {

        try {
            ProcessInstance processInstance = processRuntime.resume(ProcessPayloadBuilder
                    .resume()
                    .withProcessInstanceId(instanceID)
                    .build()
            );
            return ServerResponse.success(processInstance.getName());
        }
        catch(Exception e)
        {
            return ServerResponse.failed(ResultCode.POCESSINSTANCE_RESUME_ERROR);
        }
    }
    
    /**
     * 获取流程参数
     * @Author : Nexus
     * @Description :
     * @Date : 2020/12/20 23:11
     * @Param : instanceID
     * @return : AjaxResponse
     **/
    @ApiOperation(value = "获取流程参数", notes = "获取流程参数", httpMethod = "GET")
    @GetMapping("/variables")
    public ServerResponse variables(@RequestParam("instanceID") String instanceID) {
        try {
            List<VariableInstance> variableInstance = processRuntime.variables(ProcessPayloadBuilder
                    .variables()
                    .withProcessInstanceId(instanceID)
                    .build());

            return ServerResponse.success(variableInstance);
        }
        catch(Exception e)
        {
            return ServerResponse.failed(ResultCode.POCESSINSTANCE_GETPARAM_ERROR);
        }
    }
}

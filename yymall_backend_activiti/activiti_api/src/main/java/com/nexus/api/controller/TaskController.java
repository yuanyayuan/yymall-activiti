package com.nexus.api.controller;

import com.google.common.collect.Maps;
import com.nexus.common.api.ResultCode;
import com.nexus.common.api.ServerResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.api.task.model.Task;
import org.activiti.api.task.model.builders.TaskPayloadBuilder;
import org.activiti.api.task.runtime.TaskRuntime;
import org.activiti.bpmn.model.FormProperty;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.RepositoryService;
import org.apache.catalina.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**

* @Description:    任务类controller

* @Author:         Nexus

* @CreateDate:     2020/12/21 22:37

* @UpdateUser:     Nexus

* @UpdateDate:     2020/12/21 22:37

* @UpdateRemark:   修改内容

* @Version:        1.0

*/
@Api(value = "流程实例相关接口",tags = {"流程实例相关接口"})
@Validated
@RestController
@RequestMapping("/task")
@Slf4j
public class TaskController {
    @Autowired
    private TaskRuntime taskRuntime;

    @Autowired
    private ProcessRuntime processRuntime;

    @Autowired
    private RepositoryService repositoryService;


    /**
     * 获取我的待办任务
     * @Author : Nexus
     * @Description :
     * @Date : 2020/12/21 22:49
     * @Param :
     * @return : com.nexus.common.api.ServerResponse
     **/
    @ApiOperation(value = "获取我的待办任务", notes = "获取我的待办任务", httpMethod = "GET")
    @GetMapping(value = "/getTasks")
    public ServerResponse getTasks(){
        try {
            Page<Task> tasks = taskRuntime.tasks(Pageable.of(0, 100));
            List<HashMap<String, Object>> listMap = new ArrayList<HashMap<String, Object>>();
            for (Task tk : tasks.getContent()) {
                ProcessInstance processInstance = processRuntime.processInstance(tk.getProcessInstanceId());
                HashMap<String, Object> hashMap = Maps.newHashMap();
                hashMap.put("id", tk.getId());
                hashMap.put("name", tk.getName());
                hashMap.put("status", tk.getStatus());
                hashMap.put("createdDate", tk.getCreatedDate());
                //执行人，null时前台显示未拾取
                if (tk.getAssignee() == null) {
                    hashMap.put("assignee", "待拾取任务");
                } else {
                    hashMap.put("assignee", tk.getAssignee());
                }
                hashMap.put("instanceName", processInstance.getName());
                listMap.add(hashMap);
            }
            return ServerResponse.success(listMap);
        } catch (Exception e) {
            log.error("[TaskController][getTasks]发生错误,错误信息:{}",e.getMessage());
            return ServerResponse.failed(ResultCode.TASK_GET_ERROR);
        }
    }
    /**
     * 完成我的任务
     * @Author : Nexus
     * @Description :
     * @Date : 2020/12/21 22:55
     * @Param : taskID
     * @return : com.nexus.common.api.ServerResponse
     **/
    @ApiOperation(value = "完成我的任务", notes = "完成我的任务", httpMethod = "GET")
    @GetMapping(value = "/completeTask")
    public ServerResponse completeTask(@RequestParam("taskID") String taskID){
        try {
            Task task = taskRuntime.task(taskID);
            if (task.getAssignee() == null) {
                taskRuntime.claim(TaskPayloadBuilder.claim().withTaskId(task.getId()).build());
            }
            taskRuntime.complete(TaskPayloadBuilder.complete().withTaskId(task.getId())
                    //.withVariable("num", "2")//执行环节设置变量
                    .build());
            return ServerResponse.success();
        } catch (Exception e) {
            log.error("[TaskController][completeTask]发生错误,错误信息:{}",e.getMessage());
            return ServerResponse.failed(ResultCode.TASK_FINISH_ERROR);
        }
    }

    /**
     * 渲染动态表单
     * @Author : Nexus
     * @Description :
     * @Date : 2020/12/22 21:06
     * @Param : taskID
     * @return : AjaxResponse
     **/
    @ApiOperation(value = "渲染动态表单", notes = "渲染动态表单", httpMethod = "GET")
    @GetMapping(value = "/formDataShow")
    public ServerResponse formDataShow(@RequestParam("taskID") String taskID) {
        try {
            Task task = taskRuntime.task(taskID);

            UserTask userTask = (UserTask) repositoryService.getBpmnModel(task.getProcessDefinitionId())
                    .getFlowElement(task.getFormKey());

            if (userTask == null) {
                return ServerResponse.success(ResultCode.FORM_DATA_NULL);
            }
            List<FormProperty> formProperties = userTask.getFormProperties();
            List<HashMap<String, Object>> listMap = new ArrayList<HashMap<String, Object>>();
            for (FormProperty fp : formProperties) {

            }

            return ServerResponse.success(listMap);
        } catch (Exception e) {
            log.error("[TaskController][formDataShow]发生错误,错误信息:{}",e.getMessage());
            return ServerResponse.failed(ResultCode.FORM_DATA_SHOW_ERROR);
        }
    }
    //保存动态表单
}

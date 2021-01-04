package com.nexus.api.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.nexus.common.api.ResultCode;
import com.nexus.common.api.ServerResponse;
import com.nexus.common.config.GlobalConfig;
import com.nexus.service.IActivitiService;
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
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
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
    private IActivitiService activitiService;

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
//-----------------------构建表单控件历史数据字典------------------------------------------------
            //本实例所有保存的表单数据HashMap，为了快速读取控件以前环节存储的值
            HashMap<String, String> controlListMap = Maps.newHashMap();

            //读取数据库本实例下所有的表单数据
            List<HashMap<String, Object>> tempControlList = activitiService.selectFormDate(task.getProcessInstanceId());
            //将上面的list转换成k,v的形式
            for (HashMap<String, Object> ls : tempControlList) {
                controlListMap.put(ls.get("Control_ID_").toString(), ls.get("Control_VALUE_").toString());
            }
/*  ------------------------------------------------------------------------------
            FormProperty_0ueitp2-_!类型-_!名称-_!默认值-_!是否参数
            例子：
            FormProperty_0lovri0-_!string-_!姓名-_!请输入姓名-_!f
            FormProperty_1iu6onu-_!int-_!年龄-_!请输入年龄-_!s

            默认值：无、字符常量、FormProperty_开头定义过的控件ID
            是否参数：f为不是参数，s是字符，t是时间(不需要int，因为这里int等价于string)
            注：类型是可以获取到的，但是为了统一配置原则，都配置到
            */

            //注意!!!!!!!!:表单Key必须要任务编号一模一样，因为参数需要任务key，但是无法获取，只能获取表单key“task.getFormKey()”当做任务key
            UserTask userTask = (UserTask) repositoryService.getBpmnModel(task.getProcessDefinitionId())
                    .getFlowElement(task.getFormKey());

            if (userTask == null) {
                return ServerResponse.success(ResultCode.FORM_DATA_NULL);
            }
            List<FormProperty> formProperties = userTask.getFormProperties();
            List<HashMap<String, Object>> listMap = new ArrayList<HashMap<String, Object>>();
            for (FormProperty fp : formProperties) {
                String[] splitFP = fp.getId().split("-_!");

                HashMap<String, Object> hashMap = Maps.newHashMap();
                hashMap.put("id", splitFP[0]);
                hashMap.put("controlType", splitFP[1]);
                hashMap.put("controlLable", splitFP[2]);
                //默认值如果是表单控件ID
                if (splitFP[3].startsWith("FormProperty_")) {
                    //控件ID存在
                    //默认值
                    if (controlListMap.containsKey(splitFP[3])) {
                        hashMap.put("controlDefValue", controlListMap.get(splitFP[3]));
                    } else {
                        //控件ID不存在
                        hashMap.put("controlDefValue", "读取失败，检查" + splitFP[0] + "配置");
                    }
                } else {
                    //默认值如果不是表单控件ID则写入默认值
                    hashMap.put("controlDefValue", splitFP[3]);
                }
                //是否是参数
                hashMap.put("controlIsParam", splitFP[4]);
                listMap.add(hashMap);
            }

            return ServerResponse.success(listMap);
        } catch (Exception e) {
            log.error("[TaskController][formDataShow]发生错误,错误信息:{}",e.getMessage());
            return ServerResponse.failed(ResultCode.FORM_DATA_SHOW_ERROR);
        }
    }

    @ApiOperation(value = "保存动态表单", notes = "保存动态表单", httpMethod = "POST")
    @PostMapping(value = "/formDataSave")
    public ServerResponse formDataSave(@RequestParam("taskID") String taskID,
                                     @RequestParam("formData") String formData) {

        try {
            Task task = taskRuntime.task(taskID);

            //formData:控件id-_!控件值-_!是否参数!_!控件id-_!控件值-_!是否参数
            //FormProperty_0lovri0-_!不是参数-_!f!_!FormProperty_1iu6onu-_!数字参数-_!s


            HashMap<String, Object> variables = Maps.newHashMap();
            //没有任何参数
            boolean hasVariables = false;


            List<HashMap<String, Object>> listMap = Lists.newArrayList();

            //前端传来的字符串，拆分成每个控件
            String[] formDataList = formData.split("!_!");
            for (String controlItem : formDataList) {
                String[] formDataItem = controlItem.split("-_!");

                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("PROC_DEF_ID_", task.getProcessDefinitionId());
                hashMap.put("PROC_INST_ID_", task.getProcessInstanceId());
                hashMap.put("FORM_KEY_", task.getFormKey());
                hashMap.put("Control_ID_", formDataItem[0]);
                hashMap.put("Control_VALUE_", formDataItem[1]);
                listMap.add(hashMap);

                //构建参数集合
                switch (formDataItem[2]) {
                    case "f":
                        log.warn("控件值不作为参数");
                        break;
                    case "s":
                        variables.put(formDataItem[0], formDataItem[1]);
                        hasVariables = true;
                        break;
                    case "t":
                        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        variables.put(formDataItem[0], timeFormat.parse(formDataItem[2]));
                        hasVariables = true;
                        break;
                    case "b":
                        variables.put(formDataItem[0], BooleanUtils.toBoolean(formDataItem[2]));
                        hasVariables = true;
                        break;
                    default:
                        log.warn("控件参数类型配置错误：" + formDataItem[0] + "的参数类型不存在，" + formDataItem[2]);
                }
            }
            //for结束
            if (hasVariables) {
                //带参数完成任务
                taskRuntime.complete(TaskPayloadBuilder.complete().withTaskId(taskID)
                        .withVariables(variables)
                        .build());
            } else {
                taskRuntime.complete(TaskPayloadBuilder.complete().withTaskId(taskID)
                        .build());
            }
            //写入数据库
            activitiService.insertFormData(listMap);

            return ServerResponse.success(listMap);

        }catch (Exception e){
            return ServerResponse.failed(ResultCode.FORM_DATA_SAVE_ERROR);
        }

    }
}

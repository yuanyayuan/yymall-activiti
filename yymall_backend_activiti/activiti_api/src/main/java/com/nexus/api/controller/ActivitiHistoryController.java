package com.nexus.api.controller;

import com.google.common.collect.Maps;
import com.nexus.common.api.ResultCode;
import com.nexus.common.api.ServerResponse;
import com.nexus.common.config.GlobalConfig;
import com.nexus.pojo.bo.user.UserDetailsBO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.model.*;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@Api(value = "流程实例相关接口",tags = {"流程实例相关接口"})
@Validated
@RestController
@RequestMapping("/activitiHistory")
@Slf4j
public class ActivitiHistoryController {
    @Autowired
    private HistoryService historyService;

    @Autowired
    private RepositoryService repositoryService;

    /**
     * 获取当前用户历史任务
     * @Author : Nexus
     * @Description :
     * @Date : 2020/12/22 20:30
     * @Param : user
     * @return : com.nexus.common.api.ServerResponse
     **/
    @ApiOperation(value = "用户历史任务", notes = "用户历史任务", httpMethod = "GET")
    @GetMapping(value = "/getInstancesByUserName")
    public ServerResponse instancesByUser(@AuthenticationPrincipal UserDetailsBO user) {
        try {
            List<HistoricTaskInstance> historicTaskInstances = historyService.createHistoricTaskInstanceQuery()
                    .orderByHistoricTaskInstanceEndTime().asc()
                    .taskAssignee(user.getUsername())
                    .list();
            return ServerResponse.success(historicTaskInstances);
        } catch (Exception e) {
            return ServerResponse.failed(ResultCode.HISTORY_TASK_FINISH_ERROR);
        }
    }
    /**
     * 根据流程实例ID查询任务
     * @Author : Nexus
     * @Description :
     * @Date : 2020/12/22 20:41
     * @Param : piID
     * @return : com.nexus.common.api.ServerResponse
     **/
    @ApiOperation(value = "根据流程实例ID查询任务", notes = "根据流程实例ID查询任务", httpMethod = "GET")
    @GetMapping(value = "/getInstancesByPiID")
    public ServerResponse getInstancesByPiID(@RequestParam("piID") String piID) {
        try {
            List<HistoricTaskInstance> historicTaskInstances = historyService.createHistoricTaskInstanceQuery()
                    .orderByHistoricTaskInstanceEndTime().asc()
                    .processInstanceId(piID)
                    .list();
            return ServerResponse.success(historicTaskInstances);
        } catch (Exception e) {
            return ServerResponse.failed(ResultCode.HISTORY_TASK_FINISH_ERROR);
        }

    }
    //30efd675-4e95-11eb-a36e-2cf05d7b932e
    @ApiOperation(value = "高亮显示历史任务", notes = "高亮显示历史任务", httpMethod = "GET")
    @GetMapping(value = "/getHighLine")
    public ServerResponse getHighLine(@RequestParam("instanceId") String instanceId, @AuthenticationPrincipal UserDetailsBO user){
        try {
            //只选择具有给定流程实例的历史进程实例。
            HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
                    .processInstanceId(instanceId).singleResult();
            //获取bpmnModel对象
            BpmnModel bpmnModel = repositoryService.getBpmnModel(historicProcessInstance.getProcessDefinitionId());
            //因为我们这里只定义了一个Process 所以获取集合中的第一个即可
            Process process = bpmnModel.getProcesses().get(0);
            //-----------------------------组合-------------------------------
            //获取所有的FlowElement信息
            Collection<FlowElement> flowElements = process.getFlowElements();

            Map<String, String> map = Maps.newHashMap();
            for (FlowElement flowElement : flowElements) {
                //判断是否是连线
                if (flowElement instanceof SequenceFlow) {
                    SequenceFlow sequenceFlow = (SequenceFlow) flowElement;
                    String ref = sequenceFlow.getSourceRef();
                    String targetRef = sequenceFlow.getTargetRef();
                    map.put(ref + targetRef, sequenceFlow.getId());
                }
            }
            //---------------------------------------------------------------

            //-----------------------------组合-------------------------------
            //获取流程实例 历史节点(全部)
            List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery()
                    .processInstanceId(instanceId)
                    .list();

            //各个历史节点   两两组合 key
            Set<String> keyList = new HashSet<>();
            for (HistoricActivityInstance i : list) {
                for (HistoricActivityInstance j : list) {
                    if (i != j) {
                        keyList.add(i.getActivityId() + j.getActivityId());
                    }
                }
            }
            //---------------------------------------------------------------

            //高亮连线ID
            Set<String> highLine = new HashSet<>();
            keyList.forEach(s -> highLine.add(map.get(s)));

            //获取流程实例 历史节点（已完成）
            List<HistoricActivityInstance> listFinished = historyService.createHistoricActivityInstanceQuery()
                    .processInstanceId(instanceId)
                    .finished()
                    .list();
            //高亮节点ID（已经完成的节点高亮）
            Set<String> highPoint = new HashSet<>();
            listFinished.forEach(s -> highPoint.add(s.getActivityId()));


            //获取流程实例 历史节点（待办节点）
            List<HistoricActivityInstance> listUnFinished = historyService.createHistoricActivityInstanceQuery()
                    .processInstanceId(instanceId)
                    .unfinished()
                    .list();

            //需要移除的高亮连线
            Set<String> set = new HashSet<>();
            //待办高亮节点
            Set<String> waitingToDo = new HashSet<>();
            listUnFinished.forEach(s -> {
                waitingToDo.add(s.getActivityId());
                for (FlowElement flowElement : flowElements) {
                    //判断是否是 用户节点
                    if (flowElement instanceof UserTask) {
                        UserTask userTask = (UserTask) flowElement;

                        if (userTask.getId().equals(s.getActivityId())) {
                            List<SequenceFlow> outgoingFlows = userTask.getOutgoingFlows();
                            //因为 高亮连线查询的是所有节点  两两组合 把待办 之后  往外发出的连线 也包含进去了  所以要把高亮待办节点 之后 即出的连线去掉
                            if (outgoingFlows != null && outgoingFlows.size() > 0) {
                                outgoingFlows.forEach(a -> {
                                    if (a.getSourceRef().equals(s.getActivityId())) {
                                        set.add(a.getId());
                                    }
                                });
                            }
                        }
                    }
                }
            });
            highLine.removeAll(set);
            //获取当前用户
            //User sysUser = getSysUser();
            //存放 高亮 我的办理节点
            Set<String> iDo = new HashSet<>();

            //当前用户已完成的任务
            String assigneeName = user.getUsername();
            List<HistoricTaskInstance> taskInstanceList = historyService.createHistoricTaskInstanceQuery()
                    .taskAssignee(assigneeName)
                    .finished()
                    .processInstanceId(instanceId).list();

            taskInstanceList.forEach(a -> iDo.add(a.getTaskDefinitionKey()));

            Map<String, Object> reMap = Maps.newHashMap();
            reMap.put("highPoint", highPoint);
            reMap.put("highLine", highLine);
            reMap.put("waitingToDo", waitingToDo);
            reMap.put("iDo", iDo);
            return ServerResponse.success(reMap);
        }catch (Exception e){
            return ServerResponse.failed(ResultCode.FORM_HISTORY_SHOW_ERROR);
        }
    }
}

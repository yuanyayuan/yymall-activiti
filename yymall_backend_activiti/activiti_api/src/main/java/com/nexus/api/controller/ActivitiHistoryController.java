package com.nexus.api.controller;

import com.nexus.common.api.ResultCode;
import com.nexus.common.api.ServerResponse;
import com.nexus.pojo.bo.user.UserDetailsBO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.HistoryService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "流程实例相关接口",tags = {"流程实例相关接口"})
@Validated
@RestController
@RequestMapping("/activitiHistory")
@Slf4j
public class ActivitiHistoryController {
    @Autowired
    private HistoryService historyService;

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
    //高亮显示历史任务
}

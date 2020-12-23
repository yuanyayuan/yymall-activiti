package com.nexus.api.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.nexus.common.api.ResultCode;
import com.nexus.common.api.ServerResponse;
import com.nexus.common.config.GlobalConfig;
import com.nexus.common.enums.FileType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipInputStream;

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

    /**
     * 流程部署通过上传BPMN文件或压缩包
     * @Author : Nexus
     * @Description :
     * @Date : 2020/12/20 18:58
     * @Param : multipartFile  BPMN文件
     * @Param : deploymentName BPMNJS中起的流程名
     * @return : com.nexus.common.api.ServerResponse
     **/
    @ApiOperation(value = "流程部署通过上传BPMN文件或压缩包", notes = "流程部署通过上传BPMN文件或压缩包", httpMethod = "POST")
    @PostMapping("/uploadStreamAndDeployment")
    public ServerResponse uploadStreamAndDeployment(@RequestParam("processFile") MultipartFile multipartFile,
                                                    @RequestParam("deploymentName") String deploymentName){
        // 获取上传的文件名
        String fileName = multipartFile.getOriginalFilename();
        try {
            // 得到输入流（字节流）对象
            InputStream fileInputStream = multipartFile.getInputStream();
            // 文件的扩展名
            String extension = FilenameUtils.getExtension(fileName);
            Deployment deployment = null;
            assert extension != null;
            if (FileType.zip.value.equals(extension)) {
                ZipInputStream zip = new ZipInputStream(fileInputStream);
                //初始化流程
                deployment = repositoryService.createDeployment()
                        .addZipInputStream(zip)
                        .name(deploymentName)
                        .deploy();
            } else {
                //初始化流程
                deployment = repositoryService.createDeployment()
                        .addInputStream(fileName, fileInputStream)
                        .name(deploymentName)
                        .deploy();
            }
            return ServerResponse.success(deployment.getId()+";"+fileName);
        }catch(Exception e) {
            log.error("[ProcessDefinitionController][uploadStreamAndDeployment]发生错误,错误信息:{}",e.getMessage());
            return ServerResponse.failed(ResultCode.PROCESSDEFINITION_CREATE_ERROR);
        }
    }

    /**
     * 流程部署通过上传BPMN文件中内容（BPMN字符）
     * @Author : Nexus
     * @Description :
     * @Date : 2020/12/20 14:45
     * @Param : stringBPMN BPMN文件内容
     * @Param : deploymentName 流程名
     * @return : com.nexus.common.api.ServerResponse
     **/
    @ApiOperation(value = "流程部署通过上传BPMN文件中内容", notes = "流程部署通过上传BPMN文件中内容", httpMethod = "POST")
    @PostMapping(value = "/addDeploymentByString")
    public ServerResponse addDeploymentByString(@RequestParam("stringBPMN") String stringBPMN,
                                                @RequestParam("deploymentName") String deploymentName) {
        try {
            Deployment deployment = repositoryService.createDeployment()
                    .addString("CreateWithBPMNJS.bpmn",stringBPMN)
                    .name(deploymentName)
                    .deploy();
            return ServerResponse.success(deployment.getId());
        } catch (Exception e) {
            return ServerResponse.failed(ResultCode.PROCESSDEFINITION_CREATE_ERROR);
        }
    }

    @PostMapping(value = "/addDeploymentByFileNameBPMN")
    public ServerResponse addDeploymentByFileNameBPMN(@RequestParam("deploymentFileUUID") String deploymentFileUUID,
                                                      @RequestParam("deploymentName") String deploymentName) {
        try {
            String filename = "resources/bpmn/" + deploymentFileUUID;
            //初始化流程
            Deployment deployment = repositoryService.createDeployment()
                    .addClasspathResource(filename)
                    .name(deploymentName)
                    .deploy();
            return ServerResponse.success(deployment.getId());
        } catch (Exception e) {
            return ServerResponse.failed(ResultCode.PROCESSDEFINITION_CREATE_ERROR);
        }

    }

    /**
     * 部署流程通过在线提交BPMN的xml
     * @Author : Nexus
     * @Description : 
     * @Date : 2020/12/20 14:43
     * @Param : request
     * @Param : multipartFile
     * @return : com.nexus.common.api.ServerResponse
     **/
    @ApiOperation(value = "部署流程通过在线提交BPMN的xml", notes = "部署流程通过在线提交BPMN的xml", httpMethod = "POST")
    @PostMapping("/upload")
    public ServerResponse upload(HttpServletRequest request, @RequestParam("processFile") MultipartFile multipartFile){
        if (multipartFile.isEmpty()) {
           return ServerResponse.failed("文件名为空");
        }
        // 文件名
        String fileName = multipartFile.getOriginalFilename();
        // 后缀名
        assert fileName != null;
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        // 上传后的路径
        String filePath = GlobalConfig.BPMN_PathMapping;

        //本地路径格式转上传路径格式
        filePath = filePath.replace("\\", "/");
        filePath = filePath.replace("file:", "");

        /// String filePath = request.getSession().getServletContext().getRealPath("/") + "bpmn/";
        // 新文件名
        fileName = UUID.randomUUID() + suffixName;
        File file = new File(filePath + fileName);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            multipartFile.transferTo(file);
        } catch (Exception e) {
            log.error("[ProcessDefinitionController][upload]发生错误,错误信息:{}",e.getMessage());
            return ServerResponse.failed(ResultCode.PROCESSDEFINITION_CREATE_ERROR);
        }
        return ServerResponse.success(fileName);
    }

    /**
     * 获取流程定义列表
     * @Author : Nexus
     * @Description : 获取流程定义列表
     * @Date : 2020/12/17 21:57
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
            log.error("[ProcessDefinitionController][getDefinitions]发生错误,错误信息:{}",e.getMessage());
            return ServerResponse.failed(ResultCode.PROCESSDEFINITION_GET_ERROR);
        }
    }

    /**
     * 获取流程定义XML
     * @Author : Nexus
     * @Description :
     * @Date : 2020/12/20 14:52
     * @Param : response
     * @Param : deploymentId 流程定义中的deploymentId 流程部署ID
     * @Param : resourceName 流程定义中的resourceName BPMNJS中起的资源名
     * @return : com.nexus.common.api.ServerResponse
     **/
    @ApiOperation(value = "获取流程定义XML", notes = "获取流程定义XML", httpMethod = "GET")
    @GetMapping("/getDefinitionXML")
    public void getProcessDefineXML(HttpServletResponse response,
                                    @RequestParam("deploymentId") String deploymentId,
                                    @RequestParam("resourceName") String resourceName){
        try {
            InputStream inputStream = repositoryService.getResourceAsStream(deploymentId,resourceName);
            int count = inputStream.available();
            byte[] bytes = new byte[count];
            response.setContentType("text/xml");
            OutputStream outputStream = response.getOutputStream();
            while (inputStream.read(bytes) != -1) {
                outputStream.write(bytes);
            }
            inputStream.close();
        } catch (Exception e) {
            log.error("[ProcessDefinitionController][getProcessDefineXML]发生错误,错误信息:{}",e.getMessage());
        }
    }
    /**
     * 获取流程部署列表
     * @Author : Nexus
     * @Description :
     * @Date : 2020/12/20 15:01
     * @Param :
     * @return : com.nexus.common.api.ServerResponse
     **/
    @ApiOperation(value = "获取流程部署列表", notes = "获取流程部署列表", httpMethod = "GET")
    @GetMapping("/getDeployments")
    public ServerResponse getDeployments(){
        try {

            List<HashMap<String, Object>> listMap= new ArrayList<>();
            List<Deployment> list = repositoryService.createDeploymentQuery().list();
            for (Deployment dep : list) {
                HashMap<String, Object> hashMap = Maps.newHashMap();
                hashMap.put("id", dep.getId());
                hashMap.put("name", dep.getName());
                hashMap.put("deploymentTime", dep.getDeploymentTime());
                listMap.add(hashMap);
            }
            return ServerResponse.success(listMap);
        } catch (Exception e) {
            log.error("[ProcessDefinitionController][getDeployments]发生错误,错误信息:{}",e.getMessage());
            return ServerResponse.failed(ResultCode.PROCESSDEPLOYMENT_GET_ERROR);
        }
    }
    /**
     * 删除流程定义
     * @Author : Nexus
     * @Description :
     * @Date : 2020/12/20 18:14
     * @Param : pdID deploymentID流程部署ID
     * @return : com.nexus.common.api.ServerResponse
     **/
    @ApiOperation(value = "删除流程定义", notes = "删除流程定义", httpMethod = "GET")
    @GetMapping("/delDefinition")
    public ServerResponse delDefinition(@RequestParam("pdId") String pdId){
        try {
            repositoryService.deleteDeployment(pdId, true);
            return ServerResponse.success(null,"删除成功");
        } catch (Exception e) {
            log.error("[ProcessDefinitionController][delDefinition]发生错误,错误信息:{}",e.getMessage());
            return ServerResponse.failed("删除失败");
        }
    }
}

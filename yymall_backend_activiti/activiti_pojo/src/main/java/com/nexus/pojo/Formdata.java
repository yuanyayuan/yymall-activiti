package com.nexus.pojo;

import java.io.Serializable;
import javax.persistence.*;

public class Formdata implements Serializable {
    /**
     * 流程定义ID
     */
    @Column(name = "PROC_DEF_ID_")
    private String procDefId;

    /**
     * 流程实例ID
     */
    @Column(name = "PROC_INST_ID_")
    private String procInstId;

    /**
     * 表单TaskKey
     */
    @Column(name = "FORM_KEY_")
    private String formKey;

    /**
     * 控件ID
     */
    @Column(name = "Control_ID_")
    private String controlId;

    /**
     * 控件存放值
     */
    @Column(name = "Control_VALUE_")
    private String controlValue;

    private static final long serialVersionUID = 1L;

    /**
     * 获取流程定义ID
     *
     * @return PROC_DEF_ID_ - 流程定义ID
     */
    public String getProcDefId() {
        return procDefId;
    }

    /**
     * 设置流程定义ID
     *
     * @param procDefId 流程定义ID
     */
    public void setProcDefId(String procDefId) {
        this.procDefId = procDefId;
    }

    /**
     * 获取流程实例ID
     *
     * @return PROC_INST_ID_ - 流程实例ID
     */
    public String getProcInstId() {
        return procInstId;
    }

    /**
     * 设置流程实例ID
     *
     * @param procInstId 流程实例ID
     */
    public void setProcInstId(String procInstId) {
        this.procInstId = procInstId;
    }

    /**
     * 获取表单TaskKey
     *
     * @return FORM_KEY_ - 表单TaskKey
     */
    public String getFormKey() {
        return formKey;
    }

    /**
     * 设置表单TaskKey
     *
     * @param formKey 表单TaskKey
     */
    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    /**
     * 获取控件ID
     *
     * @return Control_ID_ - 控件ID
     */
    public String getControlId() {
        return controlId;
    }

    /**
     * 设置控件ID
     *
     * @param controlId 控件ID
     */
    public void setControlId(String controlId) {
        this.controlId = controlId;
    }

    /**
     * 获取控件存放值
     *
     * @return Control_VALUE_ - 控件存放值
     */
    public String getControlValue() {
        return controlValue;
    }

    /**
     * 设置控件存放值
     *
     * @param controlValue 控件存放值
     */
    public void setControlValue(String controlValue) {
        this.controlValue = controlValue;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", procDefId=").append(procDefId);
        sb.append(", procInstId=").append(procInstId);
        sb.append(", formKey=").append(formKey);
        sb.append(", controlId=").append(controlId);
        sb.append(", controlValue=").append(controlValue);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
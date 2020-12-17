package com.nexus.common.enums;

/**

* @Description:    性别 枚举

* @Author:         Nexus

* @CreateDate:     2020/9/6 23:22

* @UpdateUser:     Nexus 

* @UpdateDate:     2020/9/6 23:22

* @UpdateRemark:   修改内容

* @Version:        1.0

*/
public enum Sex {
    woman(0, "女"),
    man(1, "男"),
    secret(2, "保密");

    public final Integer type;
    public final String value;

    Sex(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}

package com.nexus.common.enums;
/**

* @Description:    订单状态 枚举

* @Author:         Nexus

* @CreateDate:     2020/9/21 23:07

* @UpdateUser:     Nexus

* @UpdateDate:     2020/9/21 23:07

* @UpdateRemark:   修改内容

* @Version:        1.0

*/
public enum OrderStatusEnum {
    WAIT_PAY(10, "待付款"),
    WAIT_DELIVER(20, "已付款，待发货"),
    WAIT_RECEIVE(30, "已发货，待收货"),
    SUCCESS(40, "交易成功"),
    CLOSE(50, "交易关闭");

    public final Integer type;
    public final String value;

    OrderStatusEnum(Integer type, String value){
        this.type = type;
        this.value = value;
    }
}

package com.nexus.common.enums;

import lombok.Getter;

/**
 * @className QueueEnum
 * @description 消息队列枚举配置
 * @author LiYuan
 * @date 2020/10/16
**/
@Getter
public enum QueueEnum {
    /**

     * 消息通知队列

     */

    QUEUE_ORDER_CANCEL("mall.order.direct", "mall.order.cancel", "mall.order.cancel"),

    /**

     * 消息通知ttl队列

     */

    QUEUE_TTL_ORDER_CANCEL("mall.order.direct.ttl", "mall.order.cancel.ttl", "mall.order.cancel.ttl"),

    /**
     *
     * 订单延迟插件消息队列
     *
     **/
    QUEUE_ORDER_PLUGIN_CANCEL("mall.order.direct.plugin","mall.order.cancel.plugin","mall.order.cancel.plugin");
    /**

     * 交换名称

     */

    private final String exchange;

    /**

     * 队列名称

     */

    private final String name;

    /**

     * 路由键

     */

    private final String routeKey;


    QueueEnum(String exchange, String name, String routeKey) {

        this.exchange = exchange;

        this.name = name;

        this.routeKey = routeKey;

    }
}

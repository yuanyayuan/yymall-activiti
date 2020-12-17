package com.nexus.common.enums;

public enum CategoryState {
    ONE_LEVEL(1, "一级分类"),
    TWO_LEVEL(2, "二级分类"),
    THREE_LEVEL(3, "三级分类");

    public final Integer type;
    public final String value;

    CategoryState(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}

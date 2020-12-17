package com.nexus.common.enums;

public enum ImoocPayResultCode {
    success(0, "success"),
    error(1,"error");

    public final Integer code;
    public final String message;

    ImoocPayResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}

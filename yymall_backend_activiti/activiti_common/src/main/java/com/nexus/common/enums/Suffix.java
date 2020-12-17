package com.nexus.common.enums;

import lombok.Getter;

@Getter
public enum Suffix {
    PNG_SUFFIX("png"),

    JPG_SUFFIX("jpg"),

    JPEG_SUFFIX("jpeg");

    private final String imgSuffix;

    Suffix(String imgSuffix) {

        this.imgSuffix = imgSuffix;


    }
}

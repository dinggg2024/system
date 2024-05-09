package com.example.common.enums;

public enum MenuTypeEnum {
    FOLDER("目录"),
    PAGE("菜单");

    public String value;

    MenuTypeEnum(String value) {
        this.value = value;
    }
}

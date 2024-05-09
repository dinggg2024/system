package com.example.common.enums;

public enum StatusEnum {
    CHECK_OK("审核通过"),
    CHECK_NO("审核不通过"),
    BACKED("已归还"),
    BORROW_AGAIN("续借中"),
    CHECK_BACK("归还审核中"),
    CHECK_AGAIN("续借审核中");

    public String status;

    StatusEnum(String status) {
        this.status = status;
    }
}

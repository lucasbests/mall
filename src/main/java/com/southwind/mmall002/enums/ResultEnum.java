package com.southwind.mmall002.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {
    STOCK_ERROR(1,"库存不足"),
    USER_NO_LOGIN(2,"用户未登录");

    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}

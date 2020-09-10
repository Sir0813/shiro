package com.cmdc.infrastructure.enums;

public enum  LoginEnum {

    BY_PASSWORD("Password"),

    BY_CODE("Code");

    public String getLoginType() {
        return loginType;
    }

    private String loginType;

    LoginEnum(String loginType) {
        this.loginType = loginType;
    }
}

package com.cmdc.infrastructure.shirotoken;

import org.apache.shiro.authc.UsernamePasswordToken;

public class CustomizedToken extends UsernamePasswordToken {

    /**
     * 登录类型
     */
    public String loginType;

    public CustomizedToken(final String username, final String password, final String loginType) {
        super(username, password);
        this.loginType = loginType;
    }
    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }


    @Override
    public String toString(){
        return "loginType="+ loginType +",username=" + super.getUsername()+",password="+ String.valueOf(super.getPassword());
    }

}

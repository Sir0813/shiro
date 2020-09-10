package com.cmdc.application.service;

public interface UserService {

    String passWordLogin(String userId,String passWord);

    void register( String userId, String userName,String password, String remark);

    void sendVerificationCode(String userId);

    String verificationCodeLogin(String userId, String code);
}

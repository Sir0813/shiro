package com.cmdc.application.service.impl;

import com.cmdc.application.service.UserService;
import com.cmdc.domain.service.UserDomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Order(0)
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDomainService userDomainService;

    @Override
    public String passWordLogin(String userId, String passWord) {
        return userDomainService.passwordLogin(userId,passWord);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(String userId, String userName, String password, String remark) {
        //现在开始用户注册
         userDomainService.register(userId,userName,password,remark);
    }

    @Override
    public void sendVerificationCode(String userId) {
       userDomainService.sendVerificationCode(userId);
    }

    @Override
    public String verificationCodeLogin(String userId, String code) {
        return userDomainService.verificationCodeLogin(userId,code);
    }
}

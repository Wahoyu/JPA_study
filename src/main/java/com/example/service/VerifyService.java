package com.example.service;

public interface VerifyService {
    //发送验证码
    void sendVerifyCode(String email);

    //验证码核对
    boolean doVerify(String mail, String code);
}

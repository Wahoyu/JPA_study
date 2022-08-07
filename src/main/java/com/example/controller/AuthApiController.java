package com.example.controller;

import com.example.entity.resp.RestBean;
import com.example.service.AccountService;
import com.example.service.VerifyService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/auth")
public class AuthApiController {
    @Resource
    VerifyService verifyService;
    @Resource
    AccountService accountService;

    //传输email以进行发送
    @GetMapping(value = "/verify-code")
    public RestBean<Void> verifyCode(@RequestParam("email") String email){
        try{
            verifyService.sendVerifyCode(email);
            return new RestBean<>(200,"邮件发送成功！");
        }catch(Exception e){
            return new RestBean<>(500,"邮件发送失败！");
        }
    }

    //注册controller
    @PostMapping(value = "/register")
    public RestBean<Void> register(String username,
                             String password,
                             String email,
                             String verify){
        if(verifyService.doVerify(email,verify)){
            accountService.createAccount(username, password);
            return new RestBean<>(200,"注册成功！");
        }else{
            return new RestBean<>(403,"验证码填写错误，验证失败");
        }
    }

    //登录成功提示
    //登陆失败提示

}

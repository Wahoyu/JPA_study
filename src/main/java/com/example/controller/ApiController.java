package com.example.controller;

import com.example.entity.RestBean;
import com.example.service.VerifyService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/auth")
public class ApiController {
    @Resource
    VerifyService service;

    //传输email以进行发送
    @RequestMapping(value = "/verify-code")
    public RestBean verifyCode(@RequestParam("email") String email){
        try{
            service.sendVerifyCode(email);
            return new RestBean(200,"邮件发送成功！");
        }catch(Exception e){
            return new RestBean(500,"邮件发送失败！");
        }
    }

    //注册controller
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public RestBean register(String username,
                             String password,
                             String email,
                             String verify){
        if(service.doVerify(email,verify)){
            return new RestBean(200,"注册成功！");
        }else{
            return new RestBean(403,"验证码填写错误，验证失败");
        }
    }
}

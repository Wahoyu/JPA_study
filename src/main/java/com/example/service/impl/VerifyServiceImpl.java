package com.example.service.impl;

import com.example.service.VerifyService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class VerifyServiceImpl implements VerifyService {

    @Resource
    JavaMailSender sender;
    @Resource
    StringRedisTemplate template;
    @Value("${spring.mail.username}")
    String from;

    @Override
    public void sendVerifyCode(String email) {
        //创建发邮件对象
        SimpleMailMessage message = new SimpleMailMessage();
        //创建随机数对象
        Random random = new Random();

        //创建随机数（加100000保证随机数是六位）
        int code = random.nextInt(899999)+ 100000;
        //Redis添加缓存
        template.opsForValue().set("verify:code"+email,code+"",10, TimeUnit.MINUTES);

        //发送信息详情
        message.setSubject("【皇色直播间】您的注册验证码");
        message.setText("您的注册验证码为："+code+"，三分钟内有效，请及时完成注册！如非本人操作，请您忽略。");
        message.setTo(email);
        message.setFrom(from);
        sender.send(message);
    }

    @Override
    public boolean doVerify(String mail, String code) {
        String string = template.opsForValue().get("verify:code"+mail);
        if(string == null) {
            return false;
        }
        if(!string.equals(code)){
            return false;
        }
        //删除Redis缓存
        template.delete("verify:code"+mail);
        return true;

    }
}

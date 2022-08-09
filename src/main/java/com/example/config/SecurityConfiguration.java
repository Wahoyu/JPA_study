package com.example.config;


import com.example.service.impl.AuthService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Resource
    AuthService service;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //页面授权
                .authorizeRequests()
                //专门用于登陆注册的前缀
                .antMatchers("/api/auth/**").permitAll()
                //有一定的身份才能使用的前缀
                .antMatchers("/api/**").authenticated()
                //其他的静态资源请求全部放行
                .anyRequest().permitAll()

                //登陆页面以及登录跳转
                .and()
                .formLogin()
                //初始化登陆页面
                .loginPage("/api/auth/access-deny")
                //登录使用的方法（js在使用）
                .loginProcessingUrl("/api/auth/login")
                //也是js使用的登录和失败进行的方法
                .successForwardUrl("/api/auth/login-success")
                .failureForwardUrl("/api/auth/login-failure")

                //登出操作和登出跳转
                .and()
                .logout()
                //退出时js请求的链接
                .logoutUrl("/api/auth/logout")
                //退出后跳转的页面
                .logoutSuccessUrl("/api/auth/logout-success")

                //关闭csrf
                .and()
                .csrf()
                .disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(service)
                .passwordEncoder(new BCryptPasswordEncoder());
    }
}

package com.example.config;

import com.example.service.impl.AuthService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;


@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter  {

    @Resource
    AuthService service;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/register.html", "/login.html", "/static/**","/api/auth/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login.html")
                .loginProcessingUrl("/api/auth/login")
                .successForwardUrl("/api/auth/login-success")
                .failureForwardUrl("/api/auth/login-failure")
                .and()
                .logout()
                .logoutUrl("/api/auth/logout")
                .logoutSuccessUrl("/login.html")
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

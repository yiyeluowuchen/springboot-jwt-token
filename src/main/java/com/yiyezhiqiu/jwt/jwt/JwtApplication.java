package com.yiyezhiqiu.jwt.jwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
//这里扫包好像一定要到dao层这种包，才有效
@ComponentScan(basePackages = {"com.yiyezhiqiu.jwt.jwt.*"})
@SpringBootApplication
public class JwtApplication {

    public static void main(String[] args) {
        SpringApplication.run(JwtApplication.class, args);
    }
}

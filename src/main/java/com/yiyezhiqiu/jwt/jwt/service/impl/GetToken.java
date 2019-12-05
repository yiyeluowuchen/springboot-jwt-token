package com.yiyezhiqiu.jwt.jwt.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.yiyezhiqiu.jwt.jwt.domain.Users;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 生成token
 */
@Component("GetToken")
public class GetToken {

    public String token(Users users){

        String token = "";
        token  = JWT.create().withAudience(users.getUserName()).sign(Algorithm.HMAC256(users.getPassword()));

        return token;
    }
}

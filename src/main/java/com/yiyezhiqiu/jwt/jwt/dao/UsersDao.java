package com.yiyezhiqiu.jwt.jwt.dao;

import com.yiyezhiqiu.jwt.jwt.domain.Users;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Mapper
@Repository
public interface UsersDao {

    /**
     * 注册用户
     */

    public int saveUsers(Users users);

    /**
     * 查询用户
     */

    public Users findUsers (Map<String,Object> params);

}



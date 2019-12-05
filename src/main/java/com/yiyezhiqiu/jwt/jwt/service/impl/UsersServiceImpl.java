package com.yiyezhiqiu.jwt.jwt.service.impl;

import com.yiyezhiqiu.jwt.jwt.dao.UsersDao;
import com.yiyezhiqiu.jwt.jwt.domain.Users;
import com.yiyezhiqiu.jwt.jwt.service.IUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
@Service
public class UsersServiceImpl implements IUsersService {

    @Autowired
    UsersDao usersDao;
    /**
     * 新增
     */

    @Override
    public int saveUsers(Users users) {
        int result = usersDao.saveUsers(users);

        return result ;
    }

    /**
     * 查询
     *
     */


    @Override
    public int findUsers(Users users) {

        Map<String,Object> params = new HashMap<>();
        params.put("userName",users.getUserName());
        params.put("password", users.getPassword());
        Users user =  usersDao.findUsers(params);

        if(null != user){
            return 1;
        }else{
            return 0;
        }
    }
}

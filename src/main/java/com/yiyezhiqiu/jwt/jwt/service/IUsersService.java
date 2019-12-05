package com.yiyezhiqiu.jwt.jwt.service;

import com.yiyezhiqiu.jwt.jwt.domain.Users;

public interface IUsersService {

    public int saveUsers(Users users);

    public int findUsers(Users users);

}

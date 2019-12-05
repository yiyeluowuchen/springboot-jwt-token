package com.yiyezhiqiu.jwt.jwt.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.yiyezhiqiu.jwt.jwt.annotation.LoginAnnotation;
import com.yiyezhiqiu.jwt.jwt.annotation.OtherPermit;
import com.yiyezhiqiu.jwt.jwt.domain.Users;
import com.yiyezhiqiu.jwt.jwt.service.IUsersService;
import com.yiyezhiqiu.jwt.jwt.service.impl.GetToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 实现jwt，token验证
 *  业务需求：没有登陆成功，不能访问其他页面，当登陆成功后，带入成功后返回给前端的token来访问其他页面
 *  实现：
 * 1.了解springMVC，mvc在实现时有处理controller，那么我们在处理controller前对路径进行拦截，实现WebMvcConfigure接口。
 * 2.拦截处理：实现HandlerInterceptor接口 ，在实现类中对请求的token进行验证。
 *
 *
 * 具体做法：
 * 1.springboot整合mybatis，跑通能查数据库
 * 2.写一个拦截处理器，即实现HandlerInterceptor接口
 *      2.1:思路
 *                对登陆操作不进行token验证，对非登陆操作要进行token验证
 *      2.2：做法
 *                  2.2.1：写两个注解，一个是用来对登陆进行控制，一个是其他页面进行控制
 *                  2.2.2：开始写MyInterceptor中代码
 *
 *
 * 3.实现WebMvcConfigure接口，对所有路径进行拦截，并把拦截器处理器实例加进来-》也就是实现拦截后使用拦截处理器去处理
 *
 *
 *
 *
 *
 */


@Slf4j
@RestController
@RequestMapping(value = "/users")
public class UsersController {

    @Autowired
    IUsersService usersService;
    @Resource(name = "GetToken")
    GetToken getToken;

    JSONObject jsonObject = new JSONObject();


    /**
     * 注册
     */
    @RequestMapping(value = "/register",method = RequestMethod.POST,produces = "application/json")
    public JSONObject register(@RequestBody Users users){

            int result = usersService.saveUsers(users);
            if(result >0){
                jsonObject.put("status",true);
                jsonObject.put("message","新增成功");
                return jsonObject;
            }else{
                jsonObject.put("status",false);
                jsonObject.put("message","新增失败");
                return jsonObject;
            }
    }


    /**
     * 登陆
     * @param users
     * @return
     */
    @LoginAnnotation
    @RequestMapping(value = "/login",method = RequestMethod.POST,produces = "application/json")
    public JSONObject usersLogin(@RequestBody Users users){

    log.info("userName;"+users.getUserName()+",password:"+users.getPassword());

    int result = usersService.findUsers(users);
    if(result >0){
        String token = getToken.token(users);
        jsonObject.put("users",users);
        jsonObject.put("message",token);

    }else{
         jsonObject.put("message","帐号或者密码错误");
    }
    return jsonObject;
    }


    /**
     * 其他页面
     * @return
     */
    @OtherPermit
    @RequestMapping(value = "/otherPage",method = RequestMethod.GET)
    public JSONObject otherPage(){
        //模仿下登陆后，传入token，token验证后返回有效信息
       JSONObject jsonObject = new JSONObject();
       jsonObject.put("message","验证成功");
       jsonObject.put("code",200);
        return jsonObject;
    }







}

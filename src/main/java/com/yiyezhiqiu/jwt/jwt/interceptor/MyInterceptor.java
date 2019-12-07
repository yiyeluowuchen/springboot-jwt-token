package com.yiyezhiqiu.jwt.jwt.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.yiyezhiqiu.jwt.jwt.annotation.LoginAnnotation;
import com.yiyezhiqiu.jwt.jwt.annotation.OtherPermit;
import com.yiyezhiqiu.jwt.jwt.dao.UsersDao;
import com.yiyezhiqiu.jwt.jwt.domain.Users;
import com.yiyezhiqiu.jwt.jwt.service.IUsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class MyInterceptor implements HandlerInterceptor {


    @Autowired
    IUsersService usersService;

    @Autowired
    UsersDao usersDao;


    /**
     * 这一段用来解决依赖注入不成功问题
     */
    private static MyInterceptor myInterceptor;
    @PostConstruct
    public void init (){
        myInterceptor = this;
        myInterceptor.usersDao = this.usersDao;
    }


    /**
     * springMVC正常流程时，处理器处理controller前被拦截处理
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        JSONObject jsonObject = new JSONObject();
        String token = request.getHeader("token");
        //HandleMethod包含一些controller的方法，handler 就是一个全路径方法
        HandlerMethod handlerMethod = (HandlerMethod)handler;
        Method method = handlerMethod.getMethod();
        //思路，通过判断请求的路径中的方法是否有相应的注解
        if(method.isAnnotationPresent(LoginAnnotation.class)){
            log.info("这是登陆");
            return true;
        }

        //需要验证token
        if(method.isAnnotationPresent(OtherPermit.class)){
            //重置reset
            response.reset();
            //设置编码格式
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json");
            //这里用reponse.getWriter（）后面会报重复使用的错
            ServletOutputStream pw = response.getOutputStream();

            log.info("进来验证token");
            if(StringUtils.isEmpty(token)){
                jsonObject.put("message","no token");

                pw.print(jsonObject.toJSONString());
                return false;
            }else{
                try{
                    String userName = JWT.decode(token).getAudience().get(0);
                    log.info("userName;"+userName);
                    Map<String,Object>  map = new HashMap<>();
                    map.put("userName",userName);
                    log.info("userDao:"+usersDao);
                    log.info("userservice："+usersService);
                    Users users = myInterceptor.usersDao.findUsers(map);

                    try{
                        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(users.getPassword())).build();
                        jwtVerifier.verify(token);
                        return true;
                    }catch (Exception e){
                        jsonObject.put("message","token invalid");
                        jsonObject.put("code","401");
                        pw.print(jsonObject.toJSONString());
                        return false;
                    }

                }catch(Exception e){
                    jsonObject.put("message","token invalid");
                    jsonObject.put("code","401");
                    pw.print(jsonObject.toJSONString());
                    return false;
                }


            }

        }
        return false;
    }


    /**
     * MVC时处理器处理controller后被拦截处理
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }


    /**
     * 在返回视图前拦截
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}

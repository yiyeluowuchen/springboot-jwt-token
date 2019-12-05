package com.yiyezhiqiu.jwt.jwt.configure;

import com.yiyezhiqiu.jwt.jwt.interceptor.MyInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *
 */
@Configuration
public class MyWebMvcConfigure  implements WebMvcConfigurer {

    /**
     * 拦截url并给相应拦截处理器处理
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new MyInterceptor()).addPathPatterns("/**")//拦截所有
                .excludePathPatterns("/users/register");//不拦截这个注册路径
    }


}

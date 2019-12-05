package com.yiyezhiqiu.jwt.jwt.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注明是登陆注解
 */


@Target({ElementType.METHOD,ElementType.TYPE}) //注解放在哪，方法上还是类还是哪里
@Retention(RetentionPolicy.RUNTIME)//啥时候启用，运行时
public @interface LoginAnnotation  {

   boolean  get() default true;

}

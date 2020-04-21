package com.tajkun.ad.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @program: tajkun-ad
 * @description: 不被CommonResponseAdvice拦截
 * @author: Jiakun
 * @create: 2020-04-21 17:18
 **/
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IgnoreResponseAdvice {
}
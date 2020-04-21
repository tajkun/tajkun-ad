package com.tajkun.ad.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

/**
 * @program: tajkun-ad
 * @description:
 * @author: Jiakun
 * @create: 2020-04-21 14:48
 **/
@Slf4j
@Component
public class PreRequestFilter extends ZuulFilter {
    /**
    * @Description: 指定过滤器的类型，共四种类型，PRE_TYPE:请求进来时，被路由之前调用执行
    * @return: java.lang.String
    */
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    /**
    * @Description: 定义过滤器的优先级，数字越小优先级越大，因为同一个类型的过滤器可能有多个
    * @return: int
    */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
    * @Description: 是否执行此过滤器，默认false不执行，可以根据此方法实现当某些条件到达时才执行过滤器
    * @return: boolean
    */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
    * @Description: filter执行的操作
    * @return: java.lang.Object
    */
    @Override
    public Object run() throws ZuulException {
        // 将当前时间放入zuul的请求上下文中
        RequestContext requestContext = RequestContext.getCurrentContext();
        requestContext.set("startTime",System.currentTimeMillis());
        return null;
    }
}
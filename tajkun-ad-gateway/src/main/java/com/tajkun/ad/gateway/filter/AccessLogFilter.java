package com.tajkun.ad.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: tajkun-ad
 * @description:
 * @author: Jiakun
 * @create: 2020-04-21 15:02
 **/
@Slf4j
@Component
public class AccessLogFilter extends ZuulFilter {
    /**
     * @Description: 指定过滤器的类型 POST_TYPE:在route和error过滤器之后被调用
     */
    @Override
    public String filterType() {
        return FilterConstants.POST_TYPE;
    }

    @Override
    public int filterOrder() {
        // 最后执行
        return FilterConstants.SEND_RESPONSE_FILTER_ORDER -1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        // 获取请求上下文中的开始时间
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        Long startTime = (Long) requestContext.get("startTime");
        String uri = request.getRequestURI();
        long duration = System.currentTimeMillis() - startTime;
        log.info("请求uri:" + uri + ", duration:" + duration + "ms");
        return null;
    }
}
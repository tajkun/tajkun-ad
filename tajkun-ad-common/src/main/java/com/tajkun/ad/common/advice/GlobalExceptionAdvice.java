package com.tajkun.ad.common.advice;

import com.tajkun.ad.common.exception.AdException;
import com.tajkun.ad.common.vo.CommonResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: tajkun-ad
 * @description:
 * @author: Jiakun
 * @create: 2020-04-21 17:49
 **/
@RestControllerAdvice
public class GlobalExceptionAdvice {

    // 只处理AdException
    // todo: 还有一些异常需要定义和处理
    @ExceptionHandler(value = AdException.class)
    public CommonResponse<String> handlerAdException(HttpServletRequest request, AdException exception) {
        CommonResponse<String> commonResponse = new CommonResponse<>(-1,"service err");
        commonResponse.setData(exception.getMessage());
        return commonResponse;
    }
}
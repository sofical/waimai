package com.ymhrj.ywjx.config;

import com.ymhrj.ywjx.vo.ExceptionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/*
 * Created by Administrator on 2017/11/22.
 */

@ControllerAdvice
public class ErrorHandler {
    @Autowired
    private HttpServletRequest servletRequest;

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ExceptionVo errorResposne(Exception e) {
        ExceptionVo exception = new ExceptionVo();
        exception.setCode("ERROR");
        exception.setMessage(e.getMessage());
        exception.setE(e);
        exception.setServerTime(new Date());
        exception.setHost(servletRequest.getServerName());
        exception.setRequestId(servletRequest.getRequestedSessionId());
        return exception;
    }
}

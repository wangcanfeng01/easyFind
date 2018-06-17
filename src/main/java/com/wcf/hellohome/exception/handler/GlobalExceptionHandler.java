package com.wcf.hellohome.exception.handler;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * Created by wcf on 2018/2/14.
 */
@ControllerAdvice
@Log4j2
public class GlobalExceptionHandler {
    /**
     *@note 统一异常处理
     *@author WCF
     *@time 2018/6/12 0:20
     *@since v1.0
     * @param exception
     *@return org.springframework.web.servlet.ModelAndView
     **/
    @ExceptionHandler({ Exception.class})
    public ModelAndView processException(Exception exception) {
        ModelAndView m = new ModelAndView();
        m.setViewName("error/500");
        m.addObject("error", exception.toString());
        return m;
    }

    /**
     *@note 运行时异常处理
     *@author WCF
     *@time 2018/6/12 0:20
     *@since v1.0
     * @param exception
     *@return org.springframework.web.servlet.ModelAndView
     **/
    @ExceptionHandler({ RuntimeException.class})
    public ModelAndView runtimeException(RuntimeException exception) {
        ModelAndView m = new ModelAndView();
        m.setViewName("error/500");
        m.addObject("error", exception.toString());
        return m;
    }

    /**
     *@note 非法争议请求
     *@author WCF
     *@time 2018/6/12 0:20
     *@since v1.0
     * @param e
     *@return org.springframework.web.servlet.ModelAndView
     **/
    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ModelAndView illegalArgumentException(IllegalArgumentException e) {
        ModelAndView m = new ModelAndView();
        m.setViewName("error/500");
        m.addObject("error", e.toString());
        return m;
    }

    /**
     *@note 没有找到handler
     *@author WCF
     *@time 2018/6/12 0:23
     *@since v1.0
     * @param e
     *@return org.springframework.web.servlet.ModelAndView
     **/
    @ExceptionHandler(value = {NoHandlerFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView noHandlerFoundException(Exception e) {
        ModelAndView m = new ModelAndView();
        m.setViewName("error/404");
        m.addObject("error", e.toString());
        return m;
    }
}

package com.feidea.handler;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理类
 * 拦截所有控制注解
 */
@ControllerAdvice
public class ControllerExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(HttpServletRequest request, Exception e) throws Exception {
        //记录异常信息
        logger.error("Request URL : {}, Exception : {}", request.getRequestURL(), e);
        //判断是否出现所带注解的异常出现,如果不为空,则存在异常,将异常抛出
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
            throw e;
        }
        //返回错误页面
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("url", request.getRequestURL());
        modelAndView.addObject("exception", e);
        //设置返回页面
        modelAndView.setViewName("error/error");
        return modelAndView;
    }
}

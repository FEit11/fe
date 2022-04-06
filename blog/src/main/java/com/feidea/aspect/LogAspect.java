package com.feidea.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * aop面向切面编程
 * 日志处理类
 */
@Aspect
@Component
public class LogAspect {

    //LoggerFactory.getLogger() 方法可以在输出的日志消息的时候将所属类名称一起在日志中显示
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * execution规定切面拦截哪些类
     * web包下面的所有类进行拦截
     */
    @Pointcut("execution(* com.feidea.web.*.*(..))")
    public void log() {}

    /**
     * 该方法会在切面方法之前执行
     */
    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        //接收到请求,记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //获取请求request
        HttpServletRequest request = attributes.getRequest();

        //获取请求url
        String url = request.getRequestURL().toString();
        //获取ip地址
        String ip = request.getRemoteAddr();
        //获取类方法名
        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        //获取请求参数
        Object[] args = joinPoint.getArgs();
        RequestLog requestLog = new RequestLog(url, ip, classMethod, args);
        logger.info("Request : {}",requestLog);
    }

    /**
     * 该方法会在切面方法之后执行
     */
    @After("log()")
    public void doAfter() {
//        logger.info("------doAfter------");
    }


    @AfterReturning(returning = "result",pointcut = "log()")
    public void doAfterReturn(Object result) {
        logger.info("Result : {}" + result);
    }

    private class RequestLog{
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;

        public RequestLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "RequestLog{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }
}

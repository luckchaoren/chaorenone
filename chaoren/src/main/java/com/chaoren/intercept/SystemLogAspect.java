///*
// * 文 件 名:  SystemLogAspect.java
// * 描    述:  <描述>
// * 修 改 人:  min
// * 修改时间:  2016年8月29日
// */
//package com.chaoren.intercept;
//
//import ch.qos.logback.classic.Logger;
//import com.alibaba.fastjson.JSONObject;
//import com.sensetime.dm.common.JSONUtil;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.AfterReturning;
//import org.aspectj.lang.annotation.AfterThrowing;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestAttributes;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.PrintWriter;
//
///**
// * 记录系统日志的切面
// */
//@Aspect
//@Component
//public class SystemLogAspect {
//    private final static Logger logger = (Logger) LoggerFactory.getLogger(SystemLogAspect.class);
//
//
//    @Pointcut("execution(* com.chaoren.controller.*.*(..))")
//    public void controllerAspect() {
//
//    }
//
////    /**
////     * 前置通知 用于拦截Controller层记录用户的操作
////     *
////     * @param joinPoint 切点
////     */
////    @Before("controllerAspect()")
////    public void doBefore(JoinPoint joinPoint) {
////        String methodName = joinPoint.getSignature().getName();
////        Object[] args = joinPoint.getArgs();
////        logger.info("Method：  " + methodName + " args: " + showArg(args));
////    }
//
//    @AfterReturning(returning = "result", pointcut = "controllerAspect()")
//    public void doAfterReturning(JoinPoint joinPoint, Object result) throws Throwable {
//
//        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
//        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
//        HttpServletRequest request = sra.getRequest();
//
//        String url = request.getRequestURL().toString();
//        String method = request.getMethod();
//        String uri = request.getRequestURI();
//        String queryString = request.getQueryString();
//        logger.info("Action. url: {}, method: {}, uri: {}, params: {},result: {}", url, method, uri, queryString, JSONUtil.objectToJson(result));
//    }
//
//    /**
//     * 异常通知 用于拦截异常日志
//     */
//    @AfterThrowing(pointcut = "controllerAspect()", throwing = "ex")
//    public void doAfterThrowing(JoinPoint joinPoint, Throwable ex) {
//
//        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
//        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
//        HttpServletRequest request = sra.getRequest();
//
//        String url = request.getRequestURL().toString();
//        String method = request.getMethod();
//        String uri = request.getRequestURI();
//        String queryString = request.getQueryString();
//        logger.info("Action Exception. url: {}, method: {}, uri: {}, params: {},exception: {}", url, method, uri, queryString, ex.getMessage());
//
//        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
//        String message = ex.getMessage();
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("code", "0203");
//        jsonObject.put("message", "系统内部错误");
//        jsonObject.put("data", new JSONObject());
//        convertJSONP(response,jsonObject,"");
//
//    }
//
//    public static void convertJSONP(HttpServletResponse response, JSONObject jsonObject, String callback_function_name) {
//        response.setContentType("application/json; charset=utf-8");
//        response.setContentType("json");
//
//
//        PrintWriter out=null;
//        try {
//            out = response.getWriter();
//			/*if(StringUtils.isEmpty(callback_function_name)){
//				callback_function_name = "";
//				out.print(jsonObject);
//			}else{
//				out.print(callback_function_name+"("+jsonObject+")");
//			}*/
//            out.print(jsonObject);
//        } catch (IOException e) {
//
//        }finally{
//            if(out!=null){
//                out.close();
//            }
//        }
//    }
//
//
//}

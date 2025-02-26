package com.example;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;


@Aspect
@Component
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("execution(* com.example.service.*.*(..)) || execution(* com.example.controller.*.*(..))")
    public void logForServiceLayer() {}

    @Before("logForServiceLayer()")
    public void logBeforeMethod(JoinPoint joinPoint) {
        logger.info("🔹 Method Started: {} - Arguments: {}", joinPoint.getSignature(), joinPoint.getArgs());
    }

    @AfterReturning(value = "logForServiceLayer()", returning = "result")
    public void logAfterMethod(JoinPoint joinPoint, Object result) {
        logger.info("✅ Method Completed: {} - Result: {}", joinPoint.getSignature(), result);
    }

    @AfterThrowing(value = "logForServiceLayer()", throwing = "ex")
    public void logException(JoinPoint joinPoint, Exception ex) {
        logger.error("❌ Exception in Method: {} - Exception: {}", joinPoint.getSignature(), ex.getMessage());
    }

//    @Around("@annotation(com.example.Loggable)")
//    public Object logSpecificMethod(ProceedingJoinPoint joinPoint) throws Throwable {
//        logger.info("➡️ [Custom Log] Method: {} called with args: {}",
//                joinPoint.getSignature(), Arrays.toString(joinPoint.getArgs()));
//
//        Object result = joinPoint.proceed();
//
//        logger.info("✅ [Custom Log] Method: {} returned: {}", joinPoint.getSignature(), result);
//        return result;
//    }
}
package com.hus.palindrome.aop;

import com.hus.palindrome.util.LogUtils;
import com.hus.palindrome.util.SanitizationUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("execution(* com.hus.palindrome.service..*(..)) || execution(* com.hus.palindrome.controller..*(..))")
    public Object logMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        String layer = determineLayer(joinPoint);
        String methodName = joinPoint.getSignature().getName();
        Object[] args = SanitizationUtils.sanitizeArgs(joinPoint.getArgs());

        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();

        logger.info(LogUtils.createStructuredLog("END", layer, methodName, args, result, endTime - startTime));

        return result;
    }

    @Before("execution(* com.hus.palindrome.controller..*(..))")
    public void logBeforeControllerMethods(JoinPoint joinPoint) {
        String layer = "CONTROLLER";
        String methodName = joinPoint.getSignature().getName();
        Object[] args = SanitizationUtils.sanitizeArgs(joinPoint.getArgs());

        logger.info(LogUtils.createStructuredLog("START", layer, methodName, args, null, 0));
    }

    @AfterReturning(pointcut = "execution(* com.hus.palindrome.controller..*(..))", returning = "result")
    public void logAfterControllerMethods(JoinPoint joinPoint, Object result) {
        String layer = "CONTROLLER";
        String methodName = joinPoint.getSignature().getName();

        logger.info(LogUtils.createStructuredLog("END", layer, methodName, null, result, 0));
    }

    // (SERVICE or CONTROLLER)
    private String determineLayer(ProceedingJoinPoint joinPoint) {
        if (joinPoint.getSignature().getDeclaringTypeName().contains(".controller")) {
            return "CONTROLLER";
        } else if (joinPoint.getSignature().getDeclaringTypeName().contains(".service")) {
            return "SERVICE";
        }
        return "UNKNOWN";
    }
}

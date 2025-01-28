package com.hus.palindrome.aop;

import com.hus.palindrome.util.LogUtils;
import com.hus.palindrome.util.SanitizationUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuditAspect {
    private static final Logger logger = LoggerFactory.getLogger("AUDIT");

    @Around("execution(* com.hus.palindrome.service..*(..)) || execution(* com.hus.palindrome.controller..*(..))")
    public Object auditMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        String layer = determineLayer(joinPoint);
        String methodName = joinPoint.getSignature().getName();
        Object[] args = SanitizationUtils.sanitizeArgs(joinPoint.getArgs());

        long startTime = System.currentTimeMillis();
        logger.info(LogUtils.createStructuredLog("START", layer, methodName, args, null, 0));

        Object result = joinPoint.proceed();

        long endTime = System.currentTimeMillis();
        logger.info(LogUtils.createStructuredLog("END", layer, methodName, args, result, endTime - startTime));

        return result;
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

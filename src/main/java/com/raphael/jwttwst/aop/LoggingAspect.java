package com.raphael.jwttwst.aop;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    public static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution (* com.raphael.jwttwst.service.CustomUserDetailsService.loadUserByUsername(..))")
    public void logUserDetails(JoinPoint joinPoint) {
        LOGGER.info(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName()+" Before execution");
    }

    @After("execution (* com.raphael.jwttwst.service.CustomUserDetailsService.loadUserByUsername(..))")
    public void logAfterUserDetails(JoinPoint joinPoint) {
        LOGGER.info("Completed: " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName() +" After execution");
    }

    @AfterThrowing("execution (* com.raphael.jwttwst.service.CustomUserDetailsService.loadUserByUsername(..))")
    public void logAfterThrowingUserDetails(JoinPoint joinPoint) {
        LOGGER.info("Exception in: " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName()+" After throwing");
    }

    @AfterReturning("execution(* com.raphael.jwttwst.service.CustomUserDetailsService.loadUserByUsername(..))")
    public void logAfterReturningUserDetails(JoinPoint joinPoint) {
        LOGGER.info("Successfully executed: " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName()+" After returning");
    }

}

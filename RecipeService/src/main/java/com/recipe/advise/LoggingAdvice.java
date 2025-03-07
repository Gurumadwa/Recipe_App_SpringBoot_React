//package com.recipe.advise;
//
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//@Aspect
//@Component
//public class LoggingAdvice {
//
//    private static final Logger log = LoggerFactory.getLogger(LoggingAdvice.class);
//
//    @Pointcut("execution(* com.recipe..*.*(..))")
//    public void myPointcut() {
//        // Pointcut definition
//    }
//
//    @Around("myPointcut()")
//    public Object applicationLogger(ProceedingJoinPoint pjp) throws Throwable {
//        ObjectMapper mapper = new ObjectMapper();
//        String methodName = pjp.getSignature().getName();
//        String className = pjp.getTarget().getClass().getSimpleName();
//        Object[] array = pjp.getArgs();
//
//        log.info("Method invoked: {}.{}() with arguments: {}", className, methodName, mapper.writeValueAsString(array));
//        Object object = pjp.proceed();
//        log.info("Method response: {}.{}() with response: {}", className, methodName, mapper.writeValueAsString(object));
//
//        return object;
//    }
//}

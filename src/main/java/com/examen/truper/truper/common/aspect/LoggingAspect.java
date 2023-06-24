package com.examen.truper.truper.common.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("execution(* com.examen.truper.truper.service.*.*(..))")
    public Object transcurrido(ProceedingJoinPoint point) throws Throwable {
        long startTime = System.currentTimeMillis();
        var result = point.proceed();
        log.info("Método {} ejecutado por {} milisegundos",
                point.getSignature().getName(),
                System.currentTimeMillis()-startTime);
        return result;
    }
}

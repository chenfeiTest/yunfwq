package com.redian.chat;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ApiResponseAspect {

    @Around(value = "@annotation(response)")
    public Object aroundMethod(ProceedingJoinPoint joinPoint, ApiResponse response) throws Throwable {
        Object obj = joinPoint.proceed();
        if (obj instanceof ApiResult) {
            return obj;
        }
        return new ApiResult(response.code(), obj, response.message());
    }
}

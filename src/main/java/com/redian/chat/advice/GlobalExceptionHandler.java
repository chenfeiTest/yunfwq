package com.redian.chat.advice;

import com.redian.chat.ApiResult;
import com.redian.chat.exception.BusinessException;
import com.redian.errorcommon.ExceptionAdvice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler extends ExceptionAdvice {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(BusinessException.class)
    public ApiResult handleBusinessException(BusinessException exception) {
        logger.error(exception.getMessage(), exception);
        return new ApiResult(exception.getCode(), exception.getMessage());
    }
}

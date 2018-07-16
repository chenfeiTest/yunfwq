package com.redian.chat.exception;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;

public class ValidationException extends BusinessException {

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, int code) {
        super(message, code);
    }

    public ValidationException(BindingResult result) {
        super("");
        List<String> errors = new ArrayList<>();
        for (ObjectError objectError : result.getAllErrors()) {
            errors.add(objectError.getObjectName() + ':' + objectError.getDefaultMessage());
        }
        String message = String.join(",", errors);
    }
}

package com.tsykul.joggingtracker.controller;

import com.tsykul.joggingtracker.model.ValidationErrors;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * @author KonstantinTsykulenko
 * @since 7/18/2014.
 */
@ControllerAdvice
public class RestValidationErrorRenderer {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrors processValidationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        return processFieldErrors(fieldErrors);
    }

    private ValidationErrors processFieldErrors(List<FieldError> fieldErrors) {

        List<ValidationErrors.ValidationError> errors = new ArrayList<>(fieldErrors.size());
        for (FieldError fieldError: fieldErrors) {
            errors.add(new ValidationErrors.ValidationError(fieldError.getField(), fieldError.getDefaultMessage()));
        }

        ValidationErrors dto = new ValidationErrors(errors);

        return dto;
    }
}

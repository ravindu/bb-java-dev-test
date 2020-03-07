package com.bb.java.developer.exception;

import com.bb.java.developer.ApiValidationException;
import com.bb.java.developer.model.Error;
import com.bb.java.developer.model.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;

@RequiredArgsConstructor
@Slf4j
@ControllerAdvice
public class ApplicationExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Error> handleFieldValidations(MethodArgumentNotValidException exception){
        Error error = new Error();
        error.setErrorCode(ErrorCode.VALIDATION_ERROR_CODE.getErrorCode());
        error.setErrorMessage(exception.getMessage());
        log.error("MethodArgumentNotValidException exception caught ",exception);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EventProcessorException.class)
    public ResponseEntity<Error> handleEventValidations(EventProcessorException exception){
        Error error = new Error();
        error.setErrorCode(ErrorCode.VALIDATION_ERROR_CODE.getErrorCode());
        error.setErrorMessage(exception.getMessage());
        log.error("EventProcessorException exception caught ",exception);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> handleCommonException(Exception exception){
        Error error = new Error();
        StringBuilder stringBuilder = new StringBuilder();
        HttpStatus httpStatus;

        if(exception instanceof WebExchangeBindException){
            WebExchangeBindException bindException= (WebExchangeBindException) exception;
            bindException.getAllErrors().stream().forEach(x->{
                if(x.getDefaultMessage()!=null){
                    stringBuilder.append(x.getDefaultMessage()).append(",");
                }
            });

            error.setErrorCode(ErrorCode.VALIDATION_ERROR_CODE.getErrorCode());
            error.setErrorMessage(stringBuilder.toString());
            httpStatus= HttpStatus.BAD_REQUEST;
            log.error("Spring WebExchangeBindException exception caught ",exception);

        } else if(exception instanceof ApiValidationException){
            ApiValidationException apiValidationException= (ApiValidationException) exception;
            apiValidationException.getConstraintViolations().forEach(RequestConstraintViolation ->{
                log.error(RequestConstraintViolation.getMessage());
                stringBuilder.append(RequestConstraintViolation.getPropertyPath()).append(RequestConstraintViolation.getMessage()).append(",");});

            error.setErrorCode(ErrorCode.VALIDATION_ERROR_CODE.getErrorCode());
            error.setErrorMessage(stringBuilder.toString());
            httpStatus= HttpStatus.BAD_REQUEST;
            log.error("ApiValidationException exception caught ",exception);

        } else {
            error.setErrorCode(ErrorCode.INTERNAL_ERROR_CODE.getErrorCode());
            error.setErrorMessage("Error occurred. Please contact BB support.");
            httpStatus= HttpStatus.INTERNAL_SERVER_ERROR;
            log.error("Global exception caught ",exception);
        }

        return new ResponseEntity<>(error, httpStatus);
    }
}

package com.example.order.exception;

import com.example.order.controller.MemberApiController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@Slf4j
@RestControllerAdvice(basePackageClasses = {MemberApiController.class})
public class RestControllerExceptionAdvice {

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        log.info("exception: {}", e.getMessage());
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), "validation error", e.getMessage());
        return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> runtimeExceptionHandler(RuntimeException e) {
        log.info("exception: {}", e.getMessage());
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), "RuntimeException", e.getMessage());
        return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}

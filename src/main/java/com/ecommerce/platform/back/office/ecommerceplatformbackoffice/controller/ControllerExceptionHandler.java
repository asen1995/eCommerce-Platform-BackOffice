package com.ecommerce.platform.back.office.ecommerceplatformbackoffice.controller;

import com.ecommerce.platform.back.office.ecommerceplatformbackoffice.exception.CustomerExtractingException;
import com.ecommerce.platform.back.office.ecommerceplatformbackoffice.exception.FileProductsSaveException;
import com.ecommerce.platform.back.office.ecommerceplatformbackoffice.model.ErrorMessage;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorMessage> handleException(Exception e) {
        return new ResponseEntity<>(new ErrorMessage(e.getMessage(), new Date()), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(FileUploadException.class)
    public ResponseEntity<ErrorMessage> handleException(FileUploadException e) {
        return new ResponseEntity<>(new ErrorMessage(e.getMessage(), new Date()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FileProductsSaveException.class)
    public ResponseEntity<ErrorMessage> handleException(FileProductsSaveException e) {
        return new ResponseEntity<>(new ErrorMessage(e.getMessage(), new Date()), HttpStatus.valueOf(e.getStatusCode()));
    }

    @ExceptionHandler(CustomerExtractingException.class)
    public ResponseEntity<ErrorMessage> handleException(CustomerExtractingException e) {
        return new ResponseEntity<>(new ErrorMessage(e.getMessage(), new Date()), HttpStatus.valueOf(e.getStatusCode()));
    }

}

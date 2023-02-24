package com.ecommerce.platform.back.office.ecommerceplatformbackoffice.controller;

import com.ecommerce.platform.back.office.ecommerceplatformbackoffice.exception.CustomerExtractingException;
import com.ecommerce.platform.back.office.ecommerceplatformbackoffice.exception.FileFormatNotSupportedException;
import com.ecommerce.platform.back.office.ecommerceplatformbackoffice.exception.FileNotContainProductsException;
import com.ecommerce.platform.back.office.ecommerceplatformbackoffice.exception.FileProductsSaveException;
import com.ecommerce.platform.back.office.ecommerceplatformbackoffice.model.ErrorMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class ControllerExceptionHandler {

    private static final Logger logger = LogManager.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorMessage> handleException(Exception e) {
        logger.error("Exception occurred: ", e);
        return new ResponseEntity<>(new ErrorMessage(e.getMessage(), new Date()), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(FileUploadException.class)
    public ResponseEntity<ErrorMessage> handleException(FileUploadException e) {
        logger.error("FileUploadException occurred with reason {}", e.getMessage());
        return new ResponseEntity<>(new ErrorMessage(e.getMessage(), new Date()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FileProductsSaveException.class)
    public ResponseEntity<ErrorMessage> handleException(FileProductsSaveException e) {
        logger.error("FileProductsSaveException occurred with reason {}", e.getMessage());
        return new ResponseEntity<>(new ErrorMessage(e.getMessage(), new Date()), HttpStatus.valueOf(e.getStatusCode()));
    }

    @ExceptionHandler(CustomerExtractingException.class)
    public ResponseEntity<ErrorMessage> handleException(CustomerExtractingException e) {
        logger.error("CustomerExtractingException occurred with reason {}", e.getMessage());
        return new ResponseEntity<>(new ErrorMessage(e.getMessage(), new Date()), HttpStatus.valueOf(e.getStatusCode()));
    }

    @ExceptionHandler(FileFormatNotSupportedException.class)
    public ResponseEntity<ErrorMessage> handleException(FileFormatNotSupportedException e) {
        logger.error("FileFormatNotSupportedException occurred with reason {}", e.getMessage());
        return new ResponseEntity<>(new ErrorMessage(e.getMessage(), new Date()), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler(FileNotContainProductsException.class)
    public ResponseEntity<ErrorMessage> handleException(FileNotContainProductsException e) {
        logger.error("FileNotContainProductsException occurred with reason {}", e.getMessage());
        return new ResponseEntity<>(new ErrorMessage(e.getMessage(), new Date()), HttpStatus.BAD_REQUEST);
    }
}

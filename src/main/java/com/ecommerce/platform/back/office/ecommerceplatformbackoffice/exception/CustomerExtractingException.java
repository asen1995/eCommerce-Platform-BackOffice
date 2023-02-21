package com.ecommerce.platform.back.office.ecommerceplatformbackoffice.exception;

public class CustomerExtractingException extends Exception {
    private final int statusCode;

    public CustomerExtractingException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}

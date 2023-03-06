package com.ecommerce.platform.back.office.ecommerceplatformbackoffice.exception;

public class OrderApprovingException extends Exception {
    private final int statusCode;

    public OrderApprovingException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}

package com.ecommerce.platform.back.office.ecommerceplatformbackoffice.exception;

public class OrderExtractingException  extends Exception {
    private final int statusCode;

    public OrderExtractingException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}

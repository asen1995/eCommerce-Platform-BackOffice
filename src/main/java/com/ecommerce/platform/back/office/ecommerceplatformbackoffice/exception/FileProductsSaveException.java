package com.ecommerce.platform.back.office.ecommerceplatformbackoffice.exception;

public class FileProductsSaveException extends Exception {
    private final int statusCode;

    public FileProductsSaveException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;

    }

    public int getStatusCode() {
        return statusCode;
    }
}

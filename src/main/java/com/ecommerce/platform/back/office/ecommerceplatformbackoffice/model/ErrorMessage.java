package com.ecommerce.platform.back.office.ecommerceplatformbackoffice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ErrorMessage {

    private String message;
    private Date date;
}

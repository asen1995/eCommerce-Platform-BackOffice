package com.ecommerce.platform.back.office.ecommerceplatformbackoffice.authentication;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticationResponse {
    private String jwt;
}

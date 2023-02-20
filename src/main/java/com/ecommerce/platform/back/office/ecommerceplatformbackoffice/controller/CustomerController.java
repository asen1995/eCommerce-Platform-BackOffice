package com.ecommerce.platform.back.office.ecommerceplatformbackoffice.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/customers")
public class CustomerController {


    @GetMapping
    @PreAuthorize("hasAuthority('CUSTOMER_MANAGER')")
    public String getCustomers() {
        return "customers";
    }
}

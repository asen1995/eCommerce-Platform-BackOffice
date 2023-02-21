package com.ecommerce.platform.back.office.ecommerceplatformbackoffice.controller;

import com.ecommerce.platform.back.office.ecommerceplatformbackoffice.dto.CustomerDto;
import com.ecommerce.platform.back.office.ecommerceplatformbackoffice.service.ICustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/customers")
public class CustomerController {

    private final ICustomerService customerService;

    public CustomerController(ICustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('CUSTOMER_MANAGER')")
    public ResponseEntity<List<CustomerDto>> searchCustomers(@RequestParam(value = "search") String search,
                                                             @RequestParam(value = "page", defaultValue = "0") Integer page,
                                                             @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) throws Exception {


        List<CustomerDto> customersResponse = customerService.searchCustomers(search,page, pageSize);

        return new ResponseEntity<>(customersResponse, HttpStatus.OK);
    }


}

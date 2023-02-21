package com.ecommerce.platform.back.office.ecommerceplatformbackoffice.service;

import com.ecommerce.platform.back.office.ecommerceplatformbackoffice.dto.CustomerDto;

import java.util.List;

public interface ICustomerService {
    List<CustomerDto> searchCustomers(String search, Integer page, Integer pageSize) throws Exception;
}

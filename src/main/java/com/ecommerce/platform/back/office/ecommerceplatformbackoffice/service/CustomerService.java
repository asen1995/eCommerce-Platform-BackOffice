package com.ecommerce.platform.back.office.ecommerceplatformbackoffice.service;

import com.ecommerce.platform.back.office.ecommerceplatformbackoffice.dto.CustomerDto;
import com.ecommerce.platform.back.office.ecommerceplatformbackoffice.exception.CustomerExtractingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CustomerService implements ICustomerService {

    private final RestTemplate restTemplate;
    private final String productOrderServiceUrl;

    public CustomerService(RestTemplate restTemplate, @Value("${product-order-service.url}") String productOrderServiceUrl) {
        this.restTemplate = restTemplate;
        this.productOrderServiceUrl = productOrderServiceUrl;
    }

    @Override
    public List<CustomerDto> getAllCustomers(Integer page, Integer pageSize) throws Exception {


        final String path = "/v1/customers?page=" + page + "&pageSize=" + pageSize;


        ResponseEntity<List<CustomerDto>> productDtoResponseEntity =
                restTemplate.exchange(productOrderServiceUrl + path,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {
                        });

        if (productDtoResponseEntity.getStatusCode().is2xxSuccessful()) {
            return productDtoResponseEntity.getBody();
        }

        throw new CustomerExtractingException("Call to product-order-service failed", productDtoResponseEntity.getStatusCode().value());

    }
}

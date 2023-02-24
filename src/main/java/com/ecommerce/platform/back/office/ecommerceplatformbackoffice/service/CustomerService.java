package com.ecommerce.platform.back.office.ecommerceplatformbackoffice.service;

import com.ecommerce.platform.back.office.ecommerceplatformbackoffice.dto.CustomerDto;
import com.ecommerce.platform.back.office.ecommerceplatformbackoffice.exception.CustomerExtractingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
public class CustomerService implements ICustomerService {

    private static final Logger logger = LogManager.getLogger(CustomerService.class);

    private final RestTemplate restTemplate;
    private final String productOrderServiceUrl;

    public CustomerService(RestTemplate restTemplate, @Value("${product-order-service.url}") String productOrderServiceUrl) {
        this.restTemplate = restTemplate;
        this.productOrderServiceUrl = productOrderServiceUrl;
    }

    @Override
    public List<CustomerDto> searchCustomers(String search, Integer page, Integer pageSize) throws Exception {

        logger.info("Searching customers with search: {}, page: {}, pageSize: {}", search, page, pageSize);

        String url = UriComponentsBuilder.fromUriString(productOrderServiceUrl)
                .path("/v1/customers")
                .queryParam("search", search)
                .queryParam("page", page)
                .queryParam("pageSize", pageSize)
                .build()
                .toUriString();

        String jwtToken = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);


        logger.debug("Calling product-order-service with url: {}", url);

        ResponseEntity<List<CustomerDto>> productDtoResponseEntity =
                restTemplate.exchange(url,
                        HttpMethod.GET,
                        new HttpEntity<>(headers),
                        new ParameterizedTypeReference<>() {
                        });

        if (productDtoResponseEntity.getStatusCode().is2xxSuccessful()) {
            logger.info("Found {} customers", productDtoResponseEntity.getBody().size());
            return productDtoResponseEntity.getBody();
        }

        logger.error("Failed to get customers from product-order-service. Status code: {}", productDtoResponseEntity.getStatusCode().value());

        throw new CustomerExtractingException("Failed to get customers from product-order-service. Status code: " + productDtoResponseEntity.getStatusCode().value(),
                productDtoResponseEntity.getStatusCode().value());

    }
}

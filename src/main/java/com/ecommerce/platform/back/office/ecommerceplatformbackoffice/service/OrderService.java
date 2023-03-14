package com.ecommerce.platform.back.office.ecommerceplatformbackoffice.service;

import com.ecommerce.platform.back.office.ecommerceplatformbackoffice.dto.OrderDto;
import com.ecommerce.platform.back.office.ecommerceplatformbackoffice.exception.OrderApprovingException;
import com.ecommerce.platform.back.office.ecommerceplatformbackoffice.exception.OrderExtractingException;
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
public class OrderService implements IOrderService {

    private static final Logger logger = LogManager.getLogger(OrderService.class);

    private final RestTemplate restTemplate;
    private final String productOrderServiceUrl;

    public OrderService(RestTemplate restTemplate, @Value("${product-order-service.url}") String productOrderServiceUrl) {
        this.restTemplate = restTemplate;
        this.productOrderServiceUrl = productOrderServiceUrl;
    }

    @Override
    public List<OrderDto> orderGlobalSearch(String search) throws Exception {

        String url = UriComponentsBuilder.fromUriString(productOrderServiceUrl)
                .path("/v1/orders")
                .queryParam("search", search)
                .build()
                .toUriString();

        String jwtToken = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);


        logger.debug("Calling product-order-service with url: {}", url);

        ResponseEntity<List<OrderDto>> productDtoResponseEntity =
                restTemplate.exchange(url,
                        HttpMethod.GET,
                        new HttpEntity<>(headers),
                        new ParameterizedTypeReference<>() {
                        });

        if (productDtoResponseEntity.getStatusCode().is2xxSuccessful()) {
            logger.info("Successfully got orders from product-order-service");
            return productDtoResponseEntity.getBody();
        }

        logger.error("Failed the global search for orders. Status code: {}", productDtoResponseEntity.getStatusCode().value());
        throw new OrderExtractingException("Failed the global search for orders. Status code: " + productDtoResponseEntity.getStatusCode().value(), productDtoResponseEntity.getStatusCode().value());
    }

    @Override
    public OrderDto approveOrder(Integer id) throws Exception {
        String url = UriComponentsBuilder.fromUriString(productOrderServiceUrl)
                .path(String.format("/v1/orders/approve/%d", id))
                .build()
                .toUriString();

        String jwtToken = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);


        logger.debug("Calling product-order-service with url: {}", url);

        ResponseEntity<OrderDto> productDtoResponseEntity =
                restTemplate.exchange(url,
                        HttpMethod.PUT,
                        new HttpEntity<>(headers),
                        new ParameterizedTypeReference<>() {
                        });

        if (productDtoResponseEntity.getStatusCode().is2xxSuccessful()) {
            logger.info("Successfully got approved order for id {} from product-order-service", id);
            return productDtoResponseEntity.getBody();
        }

        logger.error("Something went wrong while approving order for id {}. Status code: {}", id, productDtoResponseEntity.getStatusCode().value());
        throw new OrderApprovingException("Something went wrong while approving order for id " + id + ". Status code: " + productDtoResponseEntity.getStatusCode().value(), productDtoResponseEntity.getStatusCode().value());
    }
}

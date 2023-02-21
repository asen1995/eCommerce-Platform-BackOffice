package com.ecommerce.platform.back.office.ecommerceplatformbackoffice.service;

import com.ecommerce.platform.back.office.ecommerceplatformbackoffice.dto.ProductDto;
import com.ecommerce.platform.back.office.ecommerceplatformbackoffice.excel.ExcelReader;
import com.ecommerce.platform.back.office.ecommerceplatformbackoffice.exception.FileProductsSaveException;
import com.ecommerce.platform.back.office.ecommerceplatformbackoffice.response.ProductsUploadResponse;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ProductService implements IProductService {

    private final RestTemplate restTemplate;
    private final String productOrderServiceUrl;

    public ProductService(RestTemplate restTemplate, @Value("${product-order-service.url}") String productOrderServiceUrl) {
        this.restTemplate = restTemplate;
        this.productOrderServiceUrl = productOrderServiceUrl;
    }

    @Override
    public ProductsUploadResponse createProductsFromFile(MultipartFile file) throws FileUploadException {

        try {
            List<ProductDto> products = ExcelReader.extractProductDtos(file);

            ResponseEntity<List<ProductDto>> productDtoResponseEntity = restTemplate.exchange(productOrderServiceUrl + "/v1/products/add-many",
                    org.springframework.http.HttpMethod.POST,
                    new org.springframework.http.HttpEntity<>(products),
                    new org.springframework.core.ParameterizedTypeReference<>() {
                    });

            if (productDtoResponseEntity.getStatusCode().is2xxSuccessful()) {
                return new ProductsUploadResponse("Products saved successfully");
            }

            throw new FileProductsSaveException("Products not saved", productDtoResponseEntity.getStatusCode().value());


        } catch (Exception ex) {
            System.out.println("Failed to upload file: " + ex.getMessage());
            throw new FileUploadException("Failed to upload file: " + ex.getMessage());
        }

    }

}

package com.ecommerce.platform.back.office.ecommerceplatformbackoffice.service;

import com.ecommerce.platform.back.office.ecommerceplatformbackoffice.constants.AppConstants;
import com.ecommerce.platform.back.office.ecommerceplatformbackoffice.dto.ProductDto;
import com.ecommerce.platform.back.office.ecommerceplatformbackoffice.excel.ExcelReader;
import com.ecommerce.platform.back.office.ecommerceplatformbackoffice.exception.FileFormatNotSupportedException;
import com.ecommerce.platform.back.office.ecommerceplatformbackoffice.exception.FileProductsSaveException;
import com.ecommerce.platform.back.office.ecommerceplatformbackoffice.response.ProductsUploadResponse;
import liquibase.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ProductService implements IProductService {

    private static final Logger logger = LogManager.getLogger(ProductService.class);

    private final RestTemplate restTemplate;
    private final String productOrderServiceUrl;

    public ProductService(RestTemplate restTemplate, @Value("${product-order-service.url}") String productOrderServiceUrl) {
        this.restTemplate = restTemplate;
        this.productOrderServiceUrl = productOrderServiceUrl;
    }

    @Override
    public ProductsUploadResponse createProductsFromFile(MultipartFile file) throws Exception {

        if (file.isEmpty()) {
            logger.error("File is empty");
            throw new FileUploadException(AppConstants.FILE_EMPTY);
        }
        if(StringUtils.isBlank(file.getOriginalFilename())) {
            logger.error("File name is null");
            throw new FileUploadException(AppConstants.FILE_NAME_NULL);
        }

        if (!file.getOriginalFilename().endsWith(AppConstants.FILE_FORMAT_XLSX) && !file.getOriginalFilename().endsWith(AppConstants.FILE_FORMAT_XLS)) {
            logger.error("File format {} not supported", file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1));
            throw new FileFormatNotSupportedException(AppConstants.FILE_FORMAT_NOT_SUPPORTED);
        }
        String jwtToken = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);

        try {
            List<ProductDto> products = ExcelReader.extractProductDtos(file);

            ResponseEntity<List<ProductDto>> productDtoResponseEntity = restTemplate.exchange(productOrderServiceUrl + "/v1/products/add-many",
                    HttpMethod.POST,
                    new HttpEntity<>(products, headers),
                    new ParameterizedTypeReference<>() {
                    });

            if (productDtoResponseEntity.getStatusCode().is2xxSuccessful()) {
                logger.info("Products saved successfully");
                return new ProductsUploadResponse("Products saved successfully");
            }

            logger.error("Products not saved. Status code: {}", productDtoResponseEntity.getStatusCode().value());

            throw new FileProductsSaveException("Products not saved", productDtoResponseEntity.getStatusCode().value());


        } catch (Exception ex) {
            logger.error("Failed to upload file: {}", ex.getMessage());
            throw new FileUploadException("Failed to upload file: " + ex.getMessage());
        }

    }

}

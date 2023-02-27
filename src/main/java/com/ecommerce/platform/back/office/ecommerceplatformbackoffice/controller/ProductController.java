package com.ecommerce.platform.back.office.ecommerceplatformbackoffice.controller;

import com.ecommerce.platform.back.office.ecommerceplatformbackoffice.response.ProductsUploadResponse;
import com.ecommerce.platform.back.office.ecommerceplatformbackoffice.service.IProductService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/v1/products")
public class ProductController {

    private static final Logger logger = LogManager.getLogger(ProductController.class);
    private final IProductService productService;

    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'IMPORT_MANAGER')")
    public ResponseEntity<ProductsUploadResponse> uploadFile(@RequestParam("file") MultipartFile file) throws Exception {

        logger.info("Uploading file: {}", file.getOriginalFilename());

        ProductsUploadResponse productsUploadResponse = productService.createProductsFromFile(file);

        logger.info("Uploaded file finished successfully");
        return new ResponseEntity<>(productsUploadResponse, HttpStatus.CREATED);
    }

}

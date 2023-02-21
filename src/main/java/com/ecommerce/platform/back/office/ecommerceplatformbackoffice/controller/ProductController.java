package com.ecommerce.platform.back.office.ecommerceplatformbackoffice.controller;

import com.ecommerce.platform.back.office.ecommerceplatformbackoffice.response.ProductsUploadResponse;
import com.ecommerce.platform.back.office.ecommerceplatformbackoffice.service.IProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/v1/products")
public class ProductController {

    private final IProductService productService;

    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<ProductsUploadResponse> uploadFile(@RequestParam("file") MultipartFile file) throws Exception {

        ProductsUploadResponse productsUploadResponse = productService.createProductsFromFile(file);

        return new ResponseEntity<>(productsUploadResponse, HttpStatus.CREATED);
    }

}

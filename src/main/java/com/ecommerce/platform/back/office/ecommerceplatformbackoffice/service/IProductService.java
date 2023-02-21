package com.ecommerce.platform.back.office.ecommerceplatformbackoffice.service;

import com.ecommerce.platform.back.office.ecommerceplatformbackoffice.response.ProductsUploadResponse;
import org.springframework.web.multipart.MultipartFile;

public interface IProductService {
    ProductsUploadResponse createProductsFromFile(MultipartFile file) throws Exception;
}

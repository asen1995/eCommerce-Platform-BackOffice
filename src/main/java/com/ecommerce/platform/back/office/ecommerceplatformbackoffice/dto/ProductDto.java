package com.ecommerce.platform.back.office.ecommerceplatformbackoffice.dto;

import lombok.Data;

@Data
public class ProductDto {

    private String name;
    private String category;
    private String description;
    private Integer quantity;

}

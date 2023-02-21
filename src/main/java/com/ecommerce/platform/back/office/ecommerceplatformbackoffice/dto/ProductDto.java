package com.ecommerce.platform.back.office.ecommerceplatformbackoffice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductDto {

    private String name;
    private String category;
    private String description;
    private Integer quantity;

}

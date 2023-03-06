package com.ecommerce.platform.back.office.ecommerceplatformbackoffice.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderDto {

    private String name;
    private String comment;
    private List<ProductQuantityPairDto> productQuantityPairDtoList;

}

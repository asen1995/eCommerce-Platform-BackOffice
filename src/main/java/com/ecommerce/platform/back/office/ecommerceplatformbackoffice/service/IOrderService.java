package com.ecommerce.platform.back.office.ecommerceplatformbackoffice.service;

import com.ecommerce.platform.back.office.ecommerceplatformbackoffice.dto.OrderDto;

import java.util.List;

public interface IOrderService {

    List<OrderDto> orderGlobalSearch(String search) throws Exception;

    OrderDto approveOrder(Integer id) throws Exception;

}

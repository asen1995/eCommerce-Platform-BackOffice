package com.ecommerce.platform.back.office.ecommerceplatformbackoffice.controller;

import com.ecommerce.platform.back.office.ecommerceplatformbackoffice.dto.OrderDto;
import com.ecommerce.platform.back.office.ecommerceplatformbackoffice.service.IOrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ecommerce.platform.back.office.ecommerceplatformbackoffice.constants.AppConstants.TOPIC_NEW_ORDER;

@RestController
@RequestMapping("/v1/orders")
public class OrderController {


    private static final Logger logger = LogManager.getLogger(OrderController.class);

    private final IOrderService orderService;

    public OrderController(IOrderService orderService) {
        this.orderService = orderService;
    }


    @MessageMapping("/process-new-order")
    @SendTo(TOPIC_NEW_ORDER)
    public String processNewOrder(String message) {
        return message;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ORDER_MANAGER')")
    public ResponseEntity<List<OrderDto>> orderGlobalSearch(@RequestParam(value = "search") String search) throws Exception {

        logger.info("Searching for orders with search: {}", search);
        List<OrderDto> orderDtoList = orderService.orderGlobalSearch(search);

        logger.info("Searching for orders finished successfully");
        return new ResponseEntity<>(orderDtoList, HttpStatus.OK);
    }

    @PutMapping("/approve/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ORDER_MANAGER')")
    public ResponseEntity<OrderDto> approve(@PathVariable("id") Integer id) throws Exception {

        logger.info("Approving order with id: {} started", id);
        OrderDto orderDto = orderService.approveOrder(id);

        logger.info("Approving order with id: {} finished successfully", id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(orderDto);
    }

}

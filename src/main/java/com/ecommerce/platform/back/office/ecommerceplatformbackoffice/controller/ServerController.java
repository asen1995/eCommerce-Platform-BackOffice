package com.ecommerce.platform.back.office.ecommerceplatformbackoffice.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ServerController {


    @MessageMapping("/process-new-order")
    @SendTo("/topic/order")
    public String processNewOrder(String message) {
        return message;
    }
}

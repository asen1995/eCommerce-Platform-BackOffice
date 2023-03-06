package com.ecommerce.platform.back.office.ecommerceplatformbackoffice.controller;


import com.ecommerce.platform.back.office.ecommerceplatformbackoffice.dto.OrderDto;
import com.ecommerce.platform.back.office.ecommerceplatformbackoffice.service.IOrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(OrderController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IOrderService orderService;

    @Test
    public void testOrderGlobalSearch() throws Exception {

        OrderDto orderDto = new OrderDto();
        orderDto.setName("Laptop");
        orderDto.setComment("This is a laptop");
        orderDto.setProductQuantityPairDtoList(new ArrayList<>());


        List<OrderDto> orderDtoList = Arrays.asList(orderDto, orderDto, orderDto);

        when(orderService.orderGlobalSearch(any(String.class)))
                .thenReturn(orderDtoList);

        ObjectMapper objectMapper = new ObjectMapper();
        String expected = objectMapper.writeValueAsString(orderDtoList);

        mockMvc.perform(get("/v1/orders?search=laptop"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expected));
    }


    @Test
    public void testApproveOrder() throws Exception {

        OrderDto orderDto = new OrderDto();
        orderDto.setName("Laptop");
        orderDto.setComment("This is a laptop");

        when(orderService.approveOrder(anyInt())).thenReturn(orderDto);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(orderDto);

        mockMvc.perform(put("/v1/orders/approve/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(json));
    }
}



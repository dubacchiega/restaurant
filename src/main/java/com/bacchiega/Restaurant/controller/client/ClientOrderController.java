package com.bacchiega.Restaurant.controller.client;

import com.bacchiega.Restaurant.dto.client.OrderRequestDto;
import com.bacchiega.Restaurant.dto.client.OrderResponseDto;
import com.bacchiega.Restaurant.service.order.OrderProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class ClientOrderController {

    private final OrderProductService orderProductService;

    @PostMapping()
    public ResponseEntity<Map<String, Object>> createOrder(@RequestBody List<OrderRequestDto> orderDto) {

        OrderResponseDto order = orderProductService.createOrder(orderDto);
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "Order created successfully");
        response.put("data", order);
        return ResponseEntity.ok().body(response);
    }
}

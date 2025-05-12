package com.bacchiega.Restaurant.controller.client;

import com.bacchiega.Restaurant.dto.client.*;
import com.bacchiega.Restaurant.service.order.OrderProductService;
import com.bacchiega.Restaurant.service.order.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class ClientOrderController {

    private final OrderProductService orderProductService;
    private final PaymentService paymentService;

    @PostMapping()
    public ResponseEntity<Map<String, Object>> createOrder(@RequestBody List<OrderRequestDto> orderDto) {

        OrderResponseDto order = orderProductService.createOrder(orderDto);
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "Order created successfully");
        response.put("data", order);
        return ResponseEntity.ok().body(response);
    }

    @PatchMapping("/pay")
    public ResponseEntity<Map<String, Object>> payOrder(@RequestBody PayRequestDto payRequestDto) {
        PayResponseDto payResponseDto = paymentService.payOrder(payRequestDto.productId());

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "Order paid successfully");
        response.put("data", payResponseDto);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/get")
    public ResponseEntity<Map<String, Object>> getOrders() {
        System.out.println(">>> getOrders() foi chamado em: " + System.currentTimeMillis());

        ClientOrderDto ClientOrder = orderProductService.getClientOrders();

        System.out.println(ClientOrder);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "Orders retrieved successfully");
        response.put("data", ClientOrder);
        return ResponseEntity.ok().body(response);
    }
}

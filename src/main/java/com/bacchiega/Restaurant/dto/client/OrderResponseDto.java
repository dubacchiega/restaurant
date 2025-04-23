package com.bacchiega.Restaurant.dto.client;

import com.bacchiega.Restaurant.enums.OrderStatus;

import java.util.List;

public record OrderResponseDto(String clientName, List<ProductOrderedDto> productOrders, Double total, OrderStatus status) {
}

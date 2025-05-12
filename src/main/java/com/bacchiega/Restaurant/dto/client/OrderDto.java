package com.bacchiega.Restaurant.dto.client;

import com.bacchiega.Restaurant.enums.OrderStatus;

public record OrderDto(Long id, OrderStatus status, Double total) {
}

package com.bacchiega.Restaurant.dto.client;

import com.bacchiega.Restaurant.enums.OrderStatus;
import lombok.Builder;

import java.util.List;

@Builder
public record PayResponseDto(String clientName,
                             Double totalPaid,
                             List<String> listOfProducts,
                             OrderStatus orderStatus) {

}

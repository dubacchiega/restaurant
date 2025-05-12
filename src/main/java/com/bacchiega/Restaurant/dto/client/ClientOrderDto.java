package com.bacchiega.Restaurant.dto.client;

import com.bacchiega.Restaurant.enums.OrderStatus;

import java.util.List;

public record ClientOrderDto(String clientName, List<OrderDto> orderList) {

}

/*
{
    name:
    orderList: [
           {
              idOrder: 1,
              status: "PENDING",
              total: 100.0,
           },
              {
                  idOrder: 2,
                  status: "PENDING",
                  total: 100.0,
              }
              ]


 */
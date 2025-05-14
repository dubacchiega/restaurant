package com.bacchiega.Restaurant.service.order;

import com.bacchiega.Restaurant.config.JWTUserData;
import com.bacchiega.Restaurant.dto.client.PayResponseDto;
import com.bacchiega.Restaurant.entity.Order;
import com.bacchiega.Restaurant.enums.OrderStatus;
import com.bacchiega.Restaurant.exception.PaymentException;
import com.bacchiega.Restaurant.exception.ResourceNotFoundException;
import com.bacchiega.Restaurant.repository.OrderRepository;
import com.bacchiega.Restaurant.service.client.ClientAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {

    /*
       return data
       {
         "client name":
         "total paid":
         "list of products":
         "order status":
         }
     */

    private final OrderRepository orderRepository;
    private final MessageService messageService;

    public PayResponseDto payOrder(Long orderId) {
        JWTUserData userData = ClientAuthService.getUser();
        Order order = orderRepository.findByIdAndClientId(orderId, userData.id())
                .orElseThrow(() -> new ResourceNotFoundException("Order or Client incorrect"));

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new PaymentException("Order already paid");
        }

        order.setStatus(OrderStatus.PREPARING);
        orderRepository.save(order);
        messageService.sendMessage(OrderStatus.PREPARING, order.getClient().getPhone());

        List<String> listOfProducts = order.getProductOrders().stream()
                .map(product -> product.getProduct().getName())
                .toList();

        return PayResponseDto.builder()
                .clientName(order.getClient().getName())
                .totalPaid(order.getTotal())
                .listOfProducts(listOfProducts)
                .orderStatus(order.getStatus())
                .build();
    }


}

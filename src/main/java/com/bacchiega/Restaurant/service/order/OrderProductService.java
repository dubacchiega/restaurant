package com.bacchiega.Restaurant.service.order;

import com.bacchiega.Restaurant.config.JWTUserData;
import com.bacchiega.Restaurant.dto.client.OrderRequestDto;
import com.bacchiega.Restaurant.dto.client.OrderResponseDto;
import com.bacchiega.Restaurant.dto.client.ProductOrderedDto;
import com.bacchiega.Restaurant.entity.Client;
import com.bacchiega.Restaurant.entity.Order;
import com.bacchiega.Restaurant.entity.Product;
import com.bacchiega.Restaurant.entity.ProductOrder;
import com.bacchiega.Restaurant.exception.ClientNotFoundException;
import com.bacchiega.Restaurant.exception.OrderEmptyException;
import com.bacchiega.Restaurant.exception.ProductNotFoundException;
import com.bacchiega.Restaurant.repository.ClientRepository;
import com.bacchiega.Restaurant.repository.OrderRepository;
import com.bacchiega.Restaurant.repository.ProductOrderRepository;
import com.bacchiega.Restaurant.repository.ProductRepository;
import com.bacchiega.Restaurant.service.client.ClientAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderProductService {

    /*
    {
        "clientId": 1,
        "status": "PENDING",
        "productOrders": [
        {
            "productId": 1,
            "quantity": 2
        },
        {
            "productId": 3,
            "quantity": 1
        }
    ]
    }
     */

    private final ProductRepository productRepository;
    private final ClientRepository clientRepository;
    private final ProductOrderRepository orderProductRepository;
    private final OrderRepository orderRepository;

//    TODO criar retorno para o createOrder
//    TODO criar o endpoint de pagamento e finalização do pedido
//    /orders/{id}/pay → muda de PENDING pra PREPARING /orders/{id}/finalize → muda pra FINALIZED

    public OrderResponseDto createOrder(List<OrderRequestDto> orderDto) {

        if (orderDto.isEmpty()){
            throw new OrderEmptyException("Order list cannot be empty");
        }

        JWTUserData userData = ClientAuthService.getUser();
        Client client = clientRepository.findById(userData.id()).orElseThrow(() -> new ClientNotFoundException("Client not found"));
        Order order = new Order();
        order.setClient(client);
        orderRepository.save(order);

        double total = 0;

        List<ProductOrderedDto> productOrderedDtos = new ArrayList<>();

        for (OrderRequestDto dto : orderDto) {
            Product product = productRepository.findByName(dto.nameProduct())
                    .orElseThrow(() -> new ProductNotFoundException("Product not found"));

            total = total + (product.getPrice() * dto.quantity());

            ProductOrder productOrder = new ProductOrder();
            productOrder.setQuantity(dto.quantity());
            productOrder.setUnitPrice(product.getPrice());
            productOrder.setSubtotal(product.getPrice() * dto.quantity());
            productOrder.setOrders(order);
            productOrder.setProduct(product);

            productOrderedDtos.add(new ProductOrderedDto(productOrder.getProduct().getName(), productOrder.getQuantity(), productOrder.getSubtotal()));
            orderProductRepository.save(productOrder);
        }

        order.setTotal(total);
        orderRepository.save(order);

        return new OrderResponseDto(client.getName(), productOrderedDtos, order.getTotal(), order.getStatus());
    }

}

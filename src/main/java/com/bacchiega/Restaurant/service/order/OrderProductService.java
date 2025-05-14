package com.bacchiega.Restaurant.service.order;

import com.bacchiega.Restaurant.config.JWTUserData;
import com.bacchiega.Restaurant.dto.client.*;
import com.bacchiega.Restaurant.entity.Client;
import com.bacchiega.Restaurant.entity.Order;
import com.bacchiega.Restaurant.entity.Product;
import com.bacchiega.Restaurant.entity.ProductOrder;
import com.bacchiega.Restaurant.enums.OrderStatus;
import com.bacchiega.Restaurant.exception.*;
import com.bacchiega.Restaurant.repository.ClientRepository;
import com.bacchiega.Restaurant.repository.OrderRepository;
import com.bacchiega.Restaurant.repository.ProductOrderRepository;
import com.bacchiega.Restaurant.repository.ProductRepository;
import com.bacchiega.Restaurant.service.client.ClientAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderProductService {


    private final ProductRepository productRepository;
    private final ClientRepository clientRepository;
    private final ProductOrderRepository orderProductRepository;
    private final OrderRepository orderRepository;
    private final MessageService messageService;

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

    public ClientOrderDto getClientOrders(){
        JWTUserData userData = ClientAuthService.getUser();
        Client client = clientRepository.findById(userData.id()).orElseThrow(() -> new ClientNotFoundException("Client not found"));

        List<Order> orders = client.getOrders();

        if (orders.isEmpty()){
            throw new OrderNotFoundException("Order not found");
        }
        ClientOrderDto clientOrderDto = new ClientOrderDto(client.getName(), orders.stream().map(
                order -> new OrderDto(order.getId(), order.getStatus(), order.getTotal())).toList());

        return clientOrderDto;

//        for (Order order : orders) {
//            List<ProductOrderedDto> productOrderedDtos = new ArrayList<>();
//            for (ProductOrder productOrder : order.getProductOrders()) {
//                productOrderedDtos.add(new ProductOrderedDto(productOrder.getProduct().getName(), productOrder.getQuantity(), productOrder.getSubtotal()));
//            }
//            clientOrderDto.orderList().add(new OrdersListDto(order.getId(), productOrderedDtos, order.getTotal(), order.getStatus()));
//        }
    }

    public OrderResponseDto productFinalized(Long idProduct){
        Client client = clientRepository.findById(ClientAuthService.getUser().id()).get();
        Order order = orderRepository.findByIdAndClientId(idProduct, client.getId())
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));

        if (order.getStatus() == OrderStatus.PENDING){
            throw new OrderStatusException("Your order is pending payment and is not being prepared");
        }
        if (order.getStatus() == OrderStatus.FINALIZED){
            throw new OrderStatusException("Your order is already finalized");
        }
        if (order.getStatus() == OrderStatus.PREPARING) {
            order.setStatus(OrderStatus.FINALIZED);
            orderRepository.save(order);
            messageService.sendMessage(OrderStatus.FINALIZED, client.getPhone());
        }

        OrderResponseDto response = new OrderResponseDto(client.getName(),
                order.getProductOrders().stream().map( // pegando os produtos pedido do meu order
                        productOrder -> new ProductOrderedDto(
                                productOrder.getProduct().getName(), // nos produtos pedidos, eu estou pegando o nome do produto referenciado
                                productOrder.getQuantity(), // pegando a quantidade que foi pedida daquele produto
                                productOrder.getSubtotal())).toList( // pegando o subtotal do produto pedido
                ),
                order.getTotal(),
                order.getStatus());
        return response;
    }

}

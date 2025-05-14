package com.bacchiega.Restaurant.entity;

import com.bacchiega.Restaurant.enums.OrderStatus;
import com.bacchiega.Restaurant.service.client.ClientAuthService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Column(name = "date_time_order")
    private LocalDateTime dateTimeOrder;

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.PENDING;

    private Double total;

    // vários pedidos pode ser de um cliente
    @ManyToOne
    @JoinColumn(name = "client_id")
    @ToString.Exclude
    @JsonIgnore
    private Client client;

    // um pedido pode ter varios itens pedidos
    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    @Column(name = "product_orders")
    @ToString.Exclude
    private List<ProductOrder> productOrders;

    public Order(Client client) {
        this.client = client;
    }
}

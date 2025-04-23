package com.bacchiega.Restaurant.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ordered_product")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer quantity;

    @Column(name = "unit_price")
    private Double unitPrice;

    private Double subtotal;

    // Vários itens do pedido (ProductOrder) pertencem a um único pedido (Order)
    @ManyToOne
    @JoinColumn(name = "orders_id")
    private Order orders;

    // Cada item do pedido (ProductOrder) está associado a um único produto (Product)
    // eu posso ter varios pedidos de itens referenciando um produto
    // um produto pode estar em varios pedidos
    // cada instancia (posso ter varias) está associada a 1 produto
    // Podemos pensar na tabela ProductOrder como uma extensão da tabela Product,
    // mas com informações dinâmicas do pedido.
    // eu posso ter vários productOrder referenciado para um product
    // vou ter o meu ProductOrder de id 1 referenciando o produto de id 1
    // mas tambem posso ter o meu ProductOrder de id 2 tambem referenciando o produto de id 1
    // ou seja, são varios ProductOrder para 1 produto
    // Isso significa que vários ProductOrder podem referenciar o mesmo Product,
    // garantindo que diferentes clientes possam pedir o mesmo produto sem conflito de dados.

    // Cada linha em ProductOrder representa um pedido individual do produto.
    // O mesmo produto pode aparecer em vários pedidos, sem sobrescrever informações.
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public ProductOrder(Integer quantity, Double price, Double subTotal, Order order, Long id) {
    }
}

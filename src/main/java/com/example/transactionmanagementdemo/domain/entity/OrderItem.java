package com.example.transactionmanagementdemo.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name="orderItem")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "wholesale_price", nullable = false)
    private Double wholesale_price;

    @Column(name = "purchased_price", nullable = false)
    private Double purchased_price;

    @Column(name = "purchased_quantity", nullable = false)
    private Integer purchased_quantity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Order_id", nullable = false)
    @ToString.Exclude
    private Order order;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Product_id", nullable = false)
    @ToString.Exclude
    private Product product;

}

package com.example.transactionmanagementdemo.domain.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "wholesale_price")
    private Double wholesale_price;

    @Column(name = "retail_price", nullable = false)
    private Double retail_price;

    @Column(name = "stock_quantity")
    private Double stock_quantity;
}

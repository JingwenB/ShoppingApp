package com.example.shoppingApp.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
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
    private Integer stock_quantity;

    // one to many
    @JsonIgnore
    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<OrderItem> orderItems;

    // ManyToMany
    @ManyToMany(cascade = { CascadeType.ALL },fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinTable(
            name = "ProductWatchList",
            joinColumns = { @JoinColumn(name = "product_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id") }
    )
    private List<User> user;

    @Transient
    private Double profit = 0.0;

    @Transient
    private Integer sold_quantity = 0;




//
//    public void setOrdersProfit(){
//        this.profit =  orderItems.stream()
//                .mapToDouble((item)-> item.getPurchased_quantity()*(item.getPurchased_price()
//                        - item.getWholesale_price()))
//                .sum();
//
//    }
}

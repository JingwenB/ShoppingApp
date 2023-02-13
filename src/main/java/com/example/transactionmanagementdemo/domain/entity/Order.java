package com.example.transactionmanagementdemo.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name="order")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "order_status", nullable = false)
    private String order_status;

    @Column(name = "date_placed")
    private Timestamp date_placed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    @JsonIgnore
    private User user;

}

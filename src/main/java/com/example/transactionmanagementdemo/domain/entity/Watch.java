package com.example.transactionmanagementdemo.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="watch")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder

public class Watch {
    @Id
    @Column(name = "watch_id")
    private Integer watch_id;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "product_id")
    private Product product;
}

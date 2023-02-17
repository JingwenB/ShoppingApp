package com.example.shoppingApp.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "email", unique = true, nullable = false)
    @NaturalId
    private String email;

    @Column(name = "username", unique = true, nullable = false)
    @NaturalId
    private String username;

    @Column(name = "password",  nullable = false )
    @ToString.Exclude
    private String password;

    @Column(name = "is_admin")
    private Boolean is_admin;

    // one to many
    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<Order> orders;

    @JsonIgnore
    @ManyToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Product> products;

    @Transient
    private Double moneySpent = 0.0;
}

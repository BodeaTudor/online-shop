package com.example.onlineshop.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String name;

    @NotNull
    private int quantity;

    @NotNull
    private double price;

    @NotNull
    private String description;

    @NotNull
    private String imagePath;

    @ManyToMany(mappedBy = "products")
    private Set<Cart> carts = new HashSet<>();
}

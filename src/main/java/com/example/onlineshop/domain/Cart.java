package com.example.onlineshop.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Cart {

    @Id
    private long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Customer customer;
}

package com.example.onlineshop.transfer.product;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SaveProductRequest {

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

}

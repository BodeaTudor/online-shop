package com.example.onlineshop.transfer.cart;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AddProductToCartRequest {

    @NotNull
    private long customerId;

    @NotNull
    private long productId;
}

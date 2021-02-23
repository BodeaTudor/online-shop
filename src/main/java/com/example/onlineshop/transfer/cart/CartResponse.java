package com.example.onlineshop.transfer.cart;

import com.example.onlineshop.transfer.product.ProductInCartResponse;
import lombok.Data;

import java.util.Set;

@Data
public class CartResponse {

    private long id;

    private Set<ProductInCartResponse> products;
}

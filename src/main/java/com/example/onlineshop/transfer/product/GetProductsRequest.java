package com.example.onlineshop.transfer.product;

import lombok.Data;

@Data
public class GetProductsRequest {

    private String partialName;
    private Integer minQuantity;
}

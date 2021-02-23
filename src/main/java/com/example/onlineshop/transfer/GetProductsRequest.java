package com.example.onlineshop.transfer;

import lombok.Data;

@Data
public class GetProductsRequest {

    private String partialName;
    private Integer minQuantity;
}

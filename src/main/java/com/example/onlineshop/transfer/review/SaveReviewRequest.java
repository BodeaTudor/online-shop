package com.example.onlineshop.transfer.review;

import lombok.Data;

@Data
public class SaveReviewRequest {

    private String content;

    private Long productId;
}

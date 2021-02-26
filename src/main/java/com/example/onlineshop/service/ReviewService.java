package com.example.onlineshop.service;

import com.example.onlineshop.domain.Product;
import com.example.onlineshop.domain.Review;
import com.example.onlineshop.exceptions.ResourceNotFoundException;
import com.example.onlineshop.persistence.ReviewRepository;
import com.example.onlineshop.transfer.review.GetReviewRequest;
import com.example.onlineshop.transfer.review.ReviewResponse;
import com.example.onlineshop.transfer.review.SaveReviewRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewService.class);

    private final ReviewRepository reviewRepository;
    private final ProductService productService;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, ProductService productService) {
        this.reviewRepository = reviewRepository;
        this.productService = productService;
    }

    public Review addReviewToProduct(SaveReviewRequest request) {

        Product product = productService.getProduct(request.getProductId());

        LOGGER.info("Adding review to product with id {}: {}", request.getProductId(), request.getContent());

        Review review = new Review();
        review.setProduct(product);
        review.setContent(request.getContent());

        return reviewRepository.save(review);
    }

    public Review getReview(long id, SaveReviewRequest request) {

        if (productService.getProduct(request.getProductId()) != null) {
            LOGGER.info("Product found! Retrieving review with id {} of the product with id {}", id, request.getProductId());
        }

        return reviewRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Review with id " + id + " not found."));
    }

    public Page<ReviewResponse> getReviews(GetReviewRequest request, Pageable pageable) {


        Page<Review> reviews;

        if (request.getProductId() != null) {
            reviews = reviewRepository.findByProductId(request.getProductId(), pageable);
            LOGGER.info("Retrieving reviews for product with id: {}", request.getProductId());
        } else {
            LOGGER.info("Retrieving all reviews...");
            reviews = reviewRepository.findAll(pageable);
        }

        List<ReviewResponse> reviewResponses = new ArrayList<>();

        for (Review review : reviews.getContent()) {

            ReviewResponse reviewResponse = new ReviewResponse();

            reviewResponse.setContent(review.getContent());

            reviewResponses.add(reviewResponse);
        }

        return new PageImpl<>(reviewResponses, pageable, reviews.getTotalElements());
    }

    public Review updateReview(long id, SaveReviewRequest request) {


        Review updatedReview = getReview(id, request);
        LOGGER.info("Updating review with id {} for product with id {}", id, request.getProductId());

        BeanUtils.copyProperties(request, updatedReview);
        return reviewRepository.save(updatedReview);
    }

    public void deleteReview(long id) {

        LOGGER.info("Deleting review with id: {}", id);

        reviewRepository.deleteById(id);
    }
}

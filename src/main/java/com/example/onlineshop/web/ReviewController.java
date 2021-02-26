package com.example.onlineshop.web;

import com.example.onlineshop.domain.Review;
import com.example.onlineshop.service.ReviewService;
import com.example.onlineshop.transfer.review.GetReviewRequest;
import com.example.onlineshop.transfer.review.ReviewResponse;
import com.example.onlineshop.transfer.review.SaveReviewRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<Review> createReview(@RequestBody @Valid SaveReviewRequest request) {

        Review review = reviewService.addReviewToProduct(request);

        return new ResponseEntity<>(review, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Review> getReview(@PathVariable("id") long id, SaveReviewRequest request) {

        Review review = reviewService.getReview(id, request);

        return new ResponseEntity<>(review, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<ReviewResponse>> getReviews(GetReviewRequest request, Pageable pageable) {

        Page<ReviewResponse> reviews = reviewService.getReviews(request, pageable);

        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable("id") long id, @RequestBody @Valid SaveReviewRequest request) {

        Review updatedReview = reviewService.updateReview(id, request);

        return new ResponseEntity<>(updatedReview, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteReview(@PathVariable("id") long id) {

        reviewService.deleteReview(id);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}

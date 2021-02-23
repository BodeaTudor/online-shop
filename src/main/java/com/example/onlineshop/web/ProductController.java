package com.example.onlineshop.web;

import com.example.onlineshop.domain.Product;
import com.example.onlineshop.service.ProductService;
import com.example.onlineshop.transfer.SaveProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody @Valid SaveProductRequest request) {

        Product createdProduct = productService.createProduct(request);

        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable("id") long id) {

        Product product = productService.getProduct(id);

        return new ResponseEntity<>(product, HttpStatus.OK);
    }
}

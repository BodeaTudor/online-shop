package com.example.onlineshop.web;

import com.example.onlineshop.domain.Product;
import com.example.onlineshop.service.ProductService;
import com.example.onlineshop.transfer.GetProductsRequest;
import com.example.onlineshop.transfer.SaveProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @GetMapping
    public ResponseEntity<Page<Product>> getProducts(GetProductsRequest request, Pageable pageable) {

        Page<Product> products = productService.getProducts(request, pageable);

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") long id, @RequestBody @Valid SaveProductRequest request) {

        Product updatedProduct = productService.updateProduct(id, request);

        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProduct(@PathVariable("id") long id) {

        productService.deleteProduct(id);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping()
    public ResponseEntity deleteAllProducts() {

        productService.deleteAllProducts();

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}

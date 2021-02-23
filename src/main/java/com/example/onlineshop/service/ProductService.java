package com.example.onlineshop.service;

import com.example.onlineshop.domain.Product;
import com.example.onlineshop.exceptions.ResourceNotFoundException;
import com.example.onlineshop.persistence.ProductRepository;
import com.example.onlineshop.transfer.product.GetProductsRequest;
import com.example.onlineshop.transfer.product.ProductResponse;
import com.example.onlineshop.transfer.product.SaveProductRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(SaveProductRequest request) {

        LOGGER.info("Creating product: {}", request);

        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());
        product.setImagePath(request.getImagePath());

        return productRepository.save(product);
    }

    @Transactional
    public Page<ProductResponse> getProducts(GetProductsRequest request, Pageable pageable) {

        LOGGER.info("Retrieving products: {}", request);

        Page<Product> products;

        if (request != null && request.getPartialName() != null && request.getMinQuantity() != null) {
            products = productRepository.findByNameContainingIgnoreCaseAndQuantityGreaterThanEqual(
                    request.getPartialName(), request.getMinQuantity(), pageable);

        } else if (request != null && request.getPartialName() != null) {
            products = productRepository.findByNameContainingIgnoreCase(request.getPartialName(), pageable);

        } else {
            products = productRepository.findAll(pageable);
        }

        List<ProductResponse> productResponses = new ArrayList<>();

        for (Product product : products.getContent()) {

            ProductResponse productResponse = new ProductResponse();

            productResponse.setId(product.getId());
            productResponse.setName(product.getName());
            productResponse.setDescription(product.getDescription());
            productResponse.setPrice(product.getPrice());
            productResponse.setQuantity(product.getQuantity());
            productResponse.setImagePath(product.getImagePath());

            productResponses.add(productResponse);
        }

        return new PageImpl<>(productResponses, pageable, products.getTotalElements());
    }

    public Product getProduct(long id) {

        LOGGER.info("Retrieving product with id: {}", id);

        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product " + id + " not found."));

    }

    public Product updateProduct(long id, SaveProductRequest request) {

        LOGGER.info("Updating product with id {}: {}", id, request);

        Product product = getProduct(id);

        BeanUtils.copyProperties(request, product);

        return productRepository.save(product);
    }

    public void deleteProduct(long id) {

        LOGGER.info("Deleting product with id: {}", id);

        productRepository.deleteById(id);
    }

}

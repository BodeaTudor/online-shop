package com.example.onlineshop.service;

import com.example.onlineshop.domain.Product;
import com.example.onlineshop.exceptions.ResourceNotFoundException;
import com.example.onlineshop.persistence.ProductRepository;
import com.example.onlineshop.transfer.SaveProductRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

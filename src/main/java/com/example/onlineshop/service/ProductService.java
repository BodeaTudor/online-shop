package com.example.onlineshop.service;

import com.example.onlineshop.domain.Product;
import com.example.onlineshop.exceptions.ResourceNotFoundException;
import com.example.onlineshop.persistence.ProductRepository;
import com.example.onlineshop.transfer.GetProductsRequest;
import com.example.onlineshop.transfer.SaveProductRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<Product> getProducts(GetProductsRequest request, Pageable pageable) {

        LOGGER.info("Retrieving products: {}", request);

        if (request != null && request.getPartialName() != null && request.getMinQuantity() != null) {
            return productRepository.findByNameContainingIgnoreCaseAndQuantityGreaterThanEqual(
                    request.getPartialName(), request.getMinQuantity(), pageable);

        } else if (request != null && request.getPartialName() != null) {
            return productRepository.findByNameContainingIgnoreCase(request.getPartialName(), pageable);

        } else {
            return productRepository.findAll(pageable);
        }
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

    public void deleteAllProducts() {

        LOGGER.info("Deleting products...");

        productRepository.deleteAll();
    }
}

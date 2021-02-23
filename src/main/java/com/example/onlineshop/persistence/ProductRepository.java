package com.example.onlineshop.persistence;

import com.example.onlineshop.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByNameContainingIgnoreCase(String partialName, Pageable pageable);

    Page<Product> findByNameContainingIgnoreCaseAndQuantityGreaterThanEqual(String partialName, int minQuantity, Pageable pageable);
}

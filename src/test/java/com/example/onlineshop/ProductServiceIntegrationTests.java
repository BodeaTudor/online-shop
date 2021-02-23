package com.example.onlineshop;

import com.example.onlineshop.service.ProductService;
import com.example.onlineshop.steps.ProductSteps;
import com.example.onlineshop.transfer.SaveProductRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceIntegrationTests {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductSteps productSteps;

    @Test
    public void testCreateProduct_whenValidRequestWithAllMandatoryFields_thenReturnCreatedProduct() {

        productSteps.createProduct();
    }

    @Test
    public void testCreateProduct_whenValidRequestWithoutMandatoryFields_thenReturnCreatedProduct() {

        SaveProductRequest request = new SaveProductRequest();

        productService.createProduct(request);
    }
}

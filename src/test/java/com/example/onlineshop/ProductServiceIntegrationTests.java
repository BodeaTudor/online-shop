package com.example.onlineshop;

import com.example.onlineshop.domain.Product;
import com.example.onlineshop.exceptions.ResourceNotFoundException;
import com.example.onlineshop.service.ProductService;
import com.example.onlineshop.steps.ProductSteps;
import com.example.onlineshop.transfer.SaveProductRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

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

    @Test
    public void testGetProduct_whenExistingEntity_thenReturnProduct() {

        Product createdProduct = productSteps.createProduct();

        Product retrievedProduct = productService.getProduct(createdProduct.getId());
        assertThat(retrievedProduct, notNullValue());
        assertThat(retrievedProduct.getId(), is(createdProduct.getId()));
        assertThat(retrievedProduct.getName(), is(createdProduct.getName()));
        assertThat(retrievedProduct.getDescription(), is(createdProduct.getDescription()));
        assertThat(retrievedProduct.getPrice(), is(createdProduct.getPrice()));
        assertThat(retrievedProduct.getQuantity(), is(createdProduct.getQuantity()));
        assertThat(retrievedProduct.getImagePath(), is(createdProduct.getImagePath()));

    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetProduct_whenNonExistingEntity_thenThrowException() {

        productService.getProduct(9000);

    }
}

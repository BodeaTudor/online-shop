package com.example.onlineshop;

import com.example.onlineshop.domain.Product;
import com.example.onlineshop.exceptions.ResourceNotFoundException;
import com.example.onlineshop.service.ProductService;
import com.example.onlineshop.steps.ProductSteps;
import com.example.onlineshop.transfer.product.SaveProductRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
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

    @Test
    public void testUpdateProduct_whenValidRequest_thenReturnUpdatedProduct() {

        Product createdProduct = productSteps.createProduct();

        SaveProductRequest request = new SaveProductRequest();

        request.setName(createdProduct.getName() + " Updated");
        request.setDescription(createdProduct.getDescription() + " Updated");
        request.setPrice(createdProduct.getPrice() + 10);
        request.setQuantity(createdProduct.getQuantity() + 12);
        request.setImagePath(createdProduct.getImagePath() + "Updated");

        Product updatedProduct = productService.updateProduct(createdProduct.getId(), request);
        assertThat(updatedProduct, notNullValue());
        assertThat(updatedProduct.getId(), is(createdProduct.getId()));
        assertThat(updatedProduct.getName(), is(request.getName()));
        assertThat(updatedProduct.getDescription(), is(request.getDescription()));
        assertThat(updatedProduct.getPrice(), is(request.getPrice()));
        assertThat(updatedProduct.getQuantity(), is(request.getQuantity()));
        assertThat(updatedProduct.getImagePath(), is(request.getImagePath()));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testUpdateProduct_whenInvalidRequest_thenThrowException() {

        SaveProductRequest request = new SaveProductRequest();

        productService.updateProduct(9999, request);
    }

    @Test
    public void testDeleteProduct_whenMultipleExistingProducts_thenDeleteTheRightProduct() {

        Product createdProduct = productSteps.createProduct();
        Product createdProduct2 = productSteps.createProduct();

        productService.deleteProduct(createdProduct.getId());
        Product productInDb = productService.getProduct(createdProduct2.getId());
        assertThat(productInDb, notNullValue());
        assertThat(productInDb.getId(), is(createdProduct2.getId()));
        assertThat(productInDb.getName(), is(createdProduct2.getName()));
        assertThat(productInDb.getDescription(), is(createdProduct2.getDescription()));
        assertThat(productInDb.getPrice(), is(createdProduct2.getPrice()));
        assertThat(productInDb.getQuantity(), is(createdProduct2.getQuantity()));
        assertThat(productInDb.getImagePath(), is(createdProduct2.getImagePath()));
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void testDeleteProduct_whenNonExistingProduct_thenThrowException() {

        productService.deleteProduct(4000);
    }
}

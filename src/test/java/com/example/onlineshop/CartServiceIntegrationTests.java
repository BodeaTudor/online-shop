package com.example.onlineshop;

import com.example.onlineshop.domain.Customer;
import com.example.onlineshop.domain.Product;
import com.example.onlineshop.service.CartService;
import com.example.onlineshop.steps.CustomerSteps;
import com.example.onlineshop.steps.ProductSteps;
import com.example.onlineshop.transfer.cart.AddProductToCartRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CartServiceIntegrationTests {

    @Autowired
    private CartService cartService;

    @Autowired
    private CustomerSteps customerSteps;

    @Autowired
    private ProductSteps productSteps;

    @Test
    public void testAddToCart_whenNewCart_ThenCreateCart() {

        Customer createdCustomer = customerSteps.createCustomer();
        Product createdProduct = productSteps.createProduct();

        AddProductToCartRequest request = new AddProductToCartRequest();
        request.setCustomerId(createdCustomer.getId());
        request.setProductId(createdProduct.getId());

        cartService.addProductToCart(request);
    }
}

package com.example.onlineshop;

import com.example.onlineshop.domain.Customer;
import com.example.onlineshop.domain.Product;
import com.example.onlineshop.service.CartService;
import com.example.onlineshop.steps.CustomerSteps;
import com.example.onlineshop.steps.ProductSteps;
import com.example.onlineshop.transfer.cart.AddProductToCartRequest;
import com.example.onlineshop.transfer.cart.CartResponse;
import com.example.onlineshop.transfer.product.ProductInCartResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;


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

        CartResponse cartResponse = cartService.getCart(createdCustomer.getId());
        assertThat(cartResponse, notNullValue());
        assertThat(cartResponse.getId(), is(createdCustomer.getId()));
        assertThat(cartResponse.getProducts(), notNullValue());
        assertThat(cartResponse.getProducts(), hasSize(1));

        ProductInCartResponse productInCartResponse = cartResponse.getProducts().iterator().next();
        assertThat(productInCartResponse, notNullValue());
        assertThat(productInCartResponse.getId(), is(request.getProductId()));
        assertThat(productInCartResponse.getName(), is(createdProduct.getName()));
        assertThat(productInCartResponse.getPrice(), is(createdProduct.getPrice()));
        assertThat(productInCartResponse.getDescription(), is(createdProduct.getDescription()));
        assertThat(productInCartResponse.getImagePath(), is(createdProduct.getImagePath()));
    }
}

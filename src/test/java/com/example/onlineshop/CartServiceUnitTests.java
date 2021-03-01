package com.example.onlineshop;

import com.example.onlineshop.domain.Cart;
import com.example.onlineshop.domain.Customer;
import com.example.onlineshop.domain.Product;
import com.example.onlineshop.persistence.CartRepository;
import com.example.onlineshop.service.CartService;
import com.example.onlineshop.service.CustomerService;
import com.example.onlineshop.service.ProductService;
import com.example.onlineshop.transfer.cart.AddProductToCartRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CartServiceUnitTests {

    @Mock
    private CartRepository cartRepository;
    @Mock
    private CustomerService customerService;
    @Mock
    private ProductService productService;

    private CartService cartService;

    @Before
    public void setup() {
        cartService = new CartService(cartRepository, customerService, productService);
    }

    @Test
    public void testAddToCart_whenValidRequestForNewCart_thenThrowNoException() {

        when(cartRepository.findById(anyLong())).thenReturn(Optional.empty());

        Customer customer = new Customer();
        customer.setId(1);
        customer.setFirstName("firstName");
        customer.setLastName("lastName");
        when(customerService.getCustomer(anyLong())).thenReturn(customer);

        Product product = new Product();
        product.setId(1L);
        product.setName("Product");
        product.setPrice(12.2);
        product.setQuantity(2);
        product.setDescription("Description");
        when(productService.getProduct(anyLong())).thenReturn(product);

        when(cartRepository.save(any(Cart.class))).thenReturn(null);

        AddProductToCartRequest request = new AddProductToCartRequest();
        request.setProductId(1L);
        request.setCustomerId(1L);
        cartService.addProductToCart(request);

        verify(cartRepository).findById(anyLong());
        verify(cartRepository).save(any(Cart.class));
        verify(customerService).getCustomer(anyLong());
        verify(productService).getProduct(anyLong());
    }
}
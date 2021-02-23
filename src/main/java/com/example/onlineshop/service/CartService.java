package com.example.onlineshop.service;

import com.example.onlineshop.domain.Cart;
import com.example.onlineshop.domain.Customer;
import com.example.onlineshop.domain.Product;
import com.example.onlineshop.persistence.CartRepository;
import com.example.onlineshop.transfer.cart.AddProductToCartRequest;
import com.example.onlineshop.transfer.cart.CartResponse;
import com.example.onlineshop.transfer.product.ProductInCartResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;

import java.util.HashSet;
import java.util.Set;

@Service
public class CartService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CartService.class);

    private final CartRepository cartRepository;
    private final CustomerService customerService;
    private final ProductService productService;

    @Autowired
    public CartService(CartRepository cartRepository, CustomerService customerService, ProductService productService) {
        this.cartRepository = cartRepository;
        this.customerService = customerService;
        this.productService = productService;
    }

    @Transactional
    public void addProductToCart(AddProductToCartRequest request) {

        LOGGER.info("Adding product to cart: {}", request);

        Cart cart = cartRepository.findById(request.getCustomerId()).orElse(new Cart());

        if (cart.getCustomer() == null) {
            LOGGER.debug("Cart doesn't exist. Retrieving customer to create a new cart.");
            Customer customer = customerService.getCustomer(request.getCustomerId());
            cart.setCustomer(customer);
        }

        Product product = productService.getProduct(request.getProductId());
        cart.addToCart(product);

        cartRepository.save(cart);
    }

    @Transactional
    public CartResponse getCart(long customerId) {

        LOGGER.info("Retrieving cart for customer with id: {}", customerId);

        Cart cart = cartRepository.findById(customerId).orElseThrow(() -> new ResourceAccessException
                ("There is no cart for customer: " + customerId));

        CartResponse cartResponse = new CartResponse();
        cartResponse.setId(cart.getId());

        Set<ProductInCartResponse> products = new HashSet<>();


        for (Product product : cart.getProducts()) {
            ProductInCartResponse productInCartResponse = new ProductInCartResponse();
            productInCartResponse.setId(product.getId());
            productInCartResponse.setName(product.getName());
            productInCartResponse.setDescription(product.getDescription());
            productInCartResponse.setPrice(product.getPrice());
            productInCartResponse.setImagePath(product.getImagePath());

            products.add(productInCartResponse);
        }

        cartResponse.setProducts(products);

        return cartResponse;
    }
}

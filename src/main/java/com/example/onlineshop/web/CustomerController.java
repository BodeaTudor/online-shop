package com.example.onlineshop.web;

import com.example.onlineshop.domain.Customer;
import com.example.onlineshop.service.CustomerService;
import com.example.onlineshop.transfer.customer.SaveCustomerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody @Valid SaveCustomerRequest request) {

        Customer createdCustomer = customerService.createCustomer(request);

        return new ResponseEntity<>(createdCustomer, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable("id") long id) {

        Customer customer = customerService.getCustomer(id);

        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable("id") long id, @RequestBody @Valid SaveCustomerRequest request) {

        Customer updatedCustomer = customerService.updateCustomer(id, request);

        return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
    }
}
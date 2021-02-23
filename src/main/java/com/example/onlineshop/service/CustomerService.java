package com.example.onlineshop.service;

import com.example.onlineshop.domain.Customer;
import com.example.onlineshop.exceptions.ResourceNotFoundException;
import com.example.onlineshop.persistence.CustomerRepository;
import com.example.onlineshop.transfer.customer.SaveCustomerRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {


    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer createCustomer(SaveCustomerRequest request) {

        LOGGER.info("Creating customer: {}", request);

        Customer customer = new Customer();
        BeanUtils.copyProperties(request, customer);

        return customerRepository.save(customer);
    }

    public Customer getCustomer(long id) {

        LOGGER.info("Retrieving customer with id: {}", id);

        return customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer " + id + " not found."));
    }

    public Customer updateCustomer(long id, SaveCustomerRequest request) {

        LOGGER.info("Updating customer with id {}: {}", id, request);

        Customer customer = getCustomer(id);
        BeanUtils.copyProperties(request, customer);

        return customerRepository.save(customer);
    }
}

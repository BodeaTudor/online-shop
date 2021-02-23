package com.example.onlineshop;

import com.example.onlineshop.domain.Customer;
import com.example.onlineshop.exceptions.ResourceNotFoundException;
import com.example.onlineshop.service.CustomerService;
import com.example.onlineshop.steps.CustomerSteps;
import com.example.onlineshop.transfer.customer.SaveCustomerRequest;
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
public class CustomerServiceIntegrationTests {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerSteps customerSteps;

    @Test
    public void testCreateCustomer_whenValidRequest_thenReturnCustomer() {

        customerSteps.createCustomer();
    }

    @Test
    public void testGetCustomer_whenExistingEntity_thenReturnCustomer() {

        Customer createdCustomer = customerSteps.createCustomer();

        Customer retrievedCustomer = customerService.getCustomer(createdCustomer.getId());
        assertThat(retrievedCustomer, notNullValue());
        assertThat(retrievedCustomer.getId(), is(createdCustomer.getId()));
        assertThat(retrievedCustomer.getFirstName(), is(createdCustomer.getFirstName()));
        assertThat(retrievedCustomer.getLastName(), is(createdCustomer.getLastName()));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetCustomer_whenNonExistingEntity_thenThrowException() {

        customerService.getCustomer(9999);

    }

    @Test
    public void testUpdateCustomer_whenValidRequest_thenReturnUpdatedCustomer() {

        Customer createdCustomer = customerSteps.createCustomer();

        SaveCustomerRequest request = new SaveCustomerRequest();

        request.setFirstName(createdCustomer.getFirstName() + " Updated");
        request.setLastName(createdCustomer.getLastName() + " Updated");


        Customer updatedCustomer = customerService.updateCustomer(createdCustomer.getId(), request);
        assertThat(updatedCustomer, notNullValue());
        assertThat(updatedCustomer.getId(), is(createdCustomer.getId()));
        assertThat(updatedCustomer.getFirstName(), is(request.getFirstName()));
        assertThat(updatedCustomer.getLastName(), is(request.getLastName()));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testUpdateCustomer_whenInvalidRequest_thenThrowException() {

        SaveCustomerRequest request = new SaveCustomerRequest();

        customerService.updateCustomer(9999, request);
    }
}

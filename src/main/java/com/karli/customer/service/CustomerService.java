package com.karli.customer.service;

import com.karli.customer.model.Customer;
import com.karli.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * Creates a new customer and saves to database.
     * @param customer validated customer
     * @return created customer
     */
    public Customer createNewCustomer(Customer customer) {
        return this.customerRepository.save(customer);
    }

    public Customer findCustomerByID(long id) {
        return this.customerRepository.findById(id);
    }
}

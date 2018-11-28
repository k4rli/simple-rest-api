package com.karli.customer.service;

import com.karli.customer.exceptions.customer.CustomerNotFoundException;
import com.karli.customer.model.Customer;
import com.karli.customer.repository.CustomerRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
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
        log.info("Attempting to create new customer. Name: " + customer.getName());
        return this.customerRepository.save(customer);
    }

    /**
     * Returns customer with given ID if exists.
     * @param id target customer's ID
     * @return found customer
     */
    public Customer findCustomerByID(long id) {
        Customer targetCustomer = this.customerRepository.findById(id);
        if (targetCustomer == null) {
            throw new CustomerNotFoundException(id);
        }
        log.info("Found customer with required ID: " + id);
        return targetCustomer;
    }

    /**
     * Deletes customer with given ID if exists.
     * @param id target customer's ID
     * @return response message
     */
    public String deleteCustomerByID(long id) {
        long targetCustomer = this.customerRepository.deleteById(id);
        if (targetCustomer == 0) {
            throw new CustomerNotFoundException(id);
        }
        log.info("Customer with ID " + id + " has been deleted.");
        return "Customer with ID " + id + " has been deleted.";
    }

    public Customer updateCustomerWithModifiedFields(long id, Customer customerWithModifiedData) {
        Customer targetCustomer = this.customerRepository.findById(id);
        if (targetCustomer == null) {
            log.warn("Customer with ID " + id + " not found, couldn't modify.");
            throw new CustomerNotFoundException(id);
        }
        return targetCustomer;
    }
}

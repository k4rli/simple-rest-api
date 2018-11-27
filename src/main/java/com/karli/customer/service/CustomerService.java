package com.karli.customer.service;

import com.karli.customer.exceptions.customer.CustomerNotFoundException;
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

    /**
     * Returns customer with given ID if exists.
     * @param id target customer's ID
     * @return found customer
     */
    public Customer findCustomerByID(long id) {
        Customer targetCustomer = this.customerRepository.findById(id);
        if (targetCustomer == null) throw new CustomerNotFoundException(id);
        return targetCustomer;
    }

    /**
     * Deletes customer with given ID if exists.
     * @param id target customer's ID
     * @return response message
     */
    public String deleteCustomerByID(long id) {
        long targetCustomer = this.customerRepository.deleteById(id);
        if (targetCustomer == 0) throw new CustomerNotFoundException(id);
        return "Customer with ID " + id + " has been deleted.";
    }
}

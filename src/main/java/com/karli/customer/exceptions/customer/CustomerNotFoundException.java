package com.karli.customer.exceptions.customer;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(Long id) {
        super("Could not find customer by ID: " + id);
    }
}

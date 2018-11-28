package com.karli.customer.exceptions.customer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(Long id) {
        super("Could not find customer by ID: " + id);
        log.warn("Could not find customer by ID: " + id);
    }
}

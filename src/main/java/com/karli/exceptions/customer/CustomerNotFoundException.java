package com.karli.exceptions.customer;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(Long id) {
        super("Could not find customer by ID: " + id);
        log.warn("Could not find customer by ID: {}", id);
    }
}

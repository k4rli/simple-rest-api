package com.karli.customer.repository;

import com.karli.customer.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Customer findByNameIgnoreCase(String primaryEmail);
    Customer findById(long id);
}

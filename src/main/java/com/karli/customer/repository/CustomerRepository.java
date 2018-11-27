package com.karli.customer.repository;

import com.karli.customer.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Customer findById(long id);

    @Transactional
    @Modifying
    long deleteById(long id);
}

package com.mountbirch.user.repository;

import com.mountbirch.user.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Customer findByPrimaryEmailIgnoreCase(String primaryEmail);
}

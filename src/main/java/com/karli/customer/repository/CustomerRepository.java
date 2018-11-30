package com.karli.customer.repository;

import com.karli.customer.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

public interface CustomerRepository extends PagingAndSortingRepository<Customer, Integer> {
    Customer findById(long id);

    @Transactional
    @Modifying
    long deleteById(long id);

    Page<Customer> findAllById(PageRequest pageRequest);
}

package com.karli.customer.controller;

import com.karli.customer.model.Customer;
import com.karli.customer.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @RequestMapping(
            value = {"/addCustomer"},
            method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Customer addCustomer(@RequestBody @Valid Customer customer) {
        this.customerService.createNewCustomer(customer);
        return customer;
    }

    @RequestMapping(
            value = {"/getCustomer/{id}"},
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Customer getCustomerByName(@PathVariable long id) {
        return this.customerService.findCustomerByID(id);
    }
}

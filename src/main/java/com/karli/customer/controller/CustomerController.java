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
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Customer addCustomer(@RequestBody @Valid Customer customer) {
        return this.customerService.createNewCustomer(customer);
    }

    @RequestMapping(
            value = {"/deleteCustomer/{id}"},
            method = RequestMethod.DELETE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.TEXT_PLAIN_VALUE
    )
    public String deleteCustomer(@PathVariable long id) {
        return this.customerService.deleteCustomerByID(id);
    }

    @RequestMapping(
            value = {"/getCustomer/{id}"},
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Customer getCustomerByID(@PathVariable long id) {
        return this.customerService.findCustomerByID(id);
    }
}

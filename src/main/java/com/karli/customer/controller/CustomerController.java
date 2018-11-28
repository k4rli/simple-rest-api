package com.karli.customer.controller;

import com.karli.commons.util.DTO;
import com.karli.customer.exceptions.missingParameter.MissingParameterException;
import com.karli.customer.model.Customer;
import com.karli.customer.model.CustomerDTO;
import com.karli.customer.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import java.util.List;

@Slf4j
@RestController
@Validated
@RequestMapping("customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @RequestMapping(
            value = {"/addCustomer"},
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public Customer addCustomer(@DTO(CustomerDTO.class) @Valid Customer customer) {
        return this.customerService.createNewCustomer(customer);
    }

    @RequestMapping(
            value = {"/deleteCustomer"},
            method = RequestMethod.DELETE,
            produces = MediaType.TEXT_PLAIN_VALUE
    )
    public String deleteCustomer(@RequestParam long id) {
        log.info("Attempting to delete customer with ID " + id + ".");
        return this.customerService.deleteCustomerByID(id);
    }

    @RequestMapping(
            value = {"/getCustomer"},
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public Customer getCustomerByID(@RequestParam long id) {
        return this.customerService.findCustomerByID(id);
    }

    @RequestMapping(
            value = {"/modifyCustomer"},
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public Customer modifyCustomer(@DTO(CustomerDTO.class) Customer customer) {
        System.out.println(customer.getId());
        if (customer.getId() == 0) throw new MissingParameterException("id");
//        return this.customerService.updateCustomerWithModifiedFields(1, customer);
        return customer;
    }
}

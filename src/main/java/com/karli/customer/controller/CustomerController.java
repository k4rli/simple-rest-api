package com.karli.customer.controller;

import com.karli.commons.util.DTO;
import com.karli.customer.exceptions.customer.CustomerNotFoundException;
import com.karli.customer.exceptions.missingParameter.MissingParameterException;
import com.karli.customer.model.Customer;
import com.karli.customer.model.CustomerDTO;
import com.karli.customer.service.CustomerService;
import com.karli.response.model.CustomResponse;
import com.karli.response.types.CustomResponseCodes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import java.util.Map;

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
    public CustomResponse addCustomer(@DTO(CustomerDTO.class) @Valid Customer customer) {
        return new CustomResponse(CustomResponseCodes.SUCCESS, this.customerService.createNewCustomer(customer));
    }

    @RequestMapping(
            value = {"/deleteCustomer"},
            method = RequestMethod.DELETE,
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public CustomResponse deleteCustomer(@RequestParam long id) {
        log.info("Attempting to delete customer with ID " + id + ".");
        try {
            this.customerService.deleteCustomerByID(id);
            log.info("Deleted customer with ID " + id + " successfully.");
            return new CustomResponse(CustomResponseCodes.SUCCESS);
        } catch (CustomerNotFoundException e) {
            log.warn("Failed to delete customer with ID " + id + ".");
            return new CustomResponse(CustomResponseCodes.ERROR, e.getMessage());
        }
    }

    @RequestMapping(
            value = {"/getCustomer"},
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public CustomResponse getCustomerByID(@RequestParam long id) {
        try {
            Customer targetCustomer = this.customerService.findCustomerByID(id);
            return new CustomResponse(CustomResponseCodes.SUCCESS, targetCustomer);
        } catch (CustomerNotFoundException e) {
            return new CustomResponse(CustomResponseCodes.ERROR, e.getMessage());
        }
    }

    @RequestMapping(
            value = {"/modifyCustomer"},
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public CustomResponse modifyCustomer(@RequestParam Map<String, String> allRequestParams, ModelMap model)
            throws MissingParameterException {
        if (!allRequestParams.containsKey("id")) throw new MissingParameterException("id");
        System.out.println(model.get("id"));
        long customerID = Long.parseLong(allRequestParams.get("id"));
        try {
            Customer currentCustomer = this.customerService.findCustomerByID(customerID);
            currentCustomer = this.customerService.updateCustomer(currentCustomer, allRequestParams);
            return new CustomResponse(CustomResponseCodes.SUCCESS, currentCustomer);
        } catch (Exception e) {
            return new CustomResponse(CustomResponseCodes.ERROR, e.getMessage());
        }
    }
}

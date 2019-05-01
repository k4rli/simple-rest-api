package com.karli.customer.controller;

import com.karli.customer.model.Customer;
import com.karli.customer.model.CustomerDTO;
import com.karli.customer.service.CustomerService;
import com.karli.exceptions.customer.CustomerNotFoundException;
import com.karli.exceptions.missingParameter.MissingParameterException;
import com.karli.response.model.CustomResponse;
import com.karli.response.types.CustomResponseCodes;
import com.karli.util.DTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@Log4j2
@RestController
@Validated
@RequestMapping("customer")
@RequiredArgsConstructor
public class CustomerController {

    final CustomerService customerService;

    @PostMapping(
            value = "/addCustomer",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public CustomResponse addCustomer(@DTO(CustomerDTO.class) @Valid Customer customer) {
        return new CustomResponse(CustomResponseCodes.SUCCESS, this.customerService.createNewCustomer(customer));
    }

    @DeleteMapping(
            value = "/deleteCustomer",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public CustomResponse deleteCustomer(@RequestParam long id) {
        log.info("Attempting to delete customer with ID " + id + ".");
        try {
            customerService.deleteCustomerByID(id);
            log.info("Deleted customer with ID {} successfully.", id);
            return new CustomResponse(CustomResponseCodes.SUCCESS);
        } catch (CustomerNotFoundException e) {
            log.warn("Failed to delete customer with ID {}.", id);
            return new CustomResponse(CustomResponseCodes.ERROR, e.getMessage());
        }
    }

    @GetMapping(
            value = "/getCustomer",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public CustomResponse getCustomerByID(@RequestParam long id) {
        try {
            Customer targetCustomer = customerService.findCustomerByID(id);
            return new CustomResponse(CustomResponseCodes.SUCCESS, targetCustomer);
        } catch (CustomerNotFoundException e) {
            return new CustomResponse(CustomResponseCodes.ERROR, e.getMessage());
        }
    }

    @PutMapping(
            value = "/modifyCustomer",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public CustomResponse modifyCustomer(@RequestParam Map<String, String> allRequestParams)
    throws MissingParameterException, CustomerNotFoundException {
        if (!allRequestParams.containsKey("id")) throw new MissingParameterException("id");
        long customerID = Long.parseLong(allRequestParams.get("id"));
        try {
            Customer currentCustomer = customerService.findCustomerByID(customerID);
            customerService.updateCustomer(currentCustomer, allRequestParams);
            return new CustomResponse(CustomResponseCodes.SUCCESS, currentCustomer);
        } catch (Exception e) {
            return new CustomResponse(CustomResponseCodes.ERROR, e.getMessage());
        }
    }

    @GetMapping(
            value = "/getCustomers",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public CustomResponse getAllCustomers(@RequestParam("page") int page, @RequestParam("limit") int limit) {
        try {
            Pageable pageableRequest = PageRequest.of(page, limit);
            Page<Customer> resultPage = this.customerService.getCustomers(pageableRequest);
            return new CustomResponse(CustomResponseCodes.SUCCESS, resultPage.getContent());
        } catch (Exception e) {
            log.error("Exception occurred when trying to find records, assuming invalid parameters", e);
            return new CustomResponse(CustomResponseCodes.ERROR, e.getMessage());
        }
    }
}

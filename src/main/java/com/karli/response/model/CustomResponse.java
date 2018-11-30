package com.karli.response.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.karli.customer.model.Customer;
import com.karli.response.types.CustomResponseCodes;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomResponse {
    @NotNull
    CustomResponseCodes result;
    Customer customer;
    String error;
    List<Customer> customers;

    public CustomResponse(CustomResponseCodes result) {
        this.result = result;
    }

    public CustomResponse(CustomResponseCodes result, Customer customer) {
        this.result = result;
        this.customer = customer;
    }

    public CustomResponse(@NotNull CustomResponseCodes result, String error) {
        this.result = result;
        this.error = error;
    }

    public CustomResponse(@NotNull CustomResponseCodes result, List<Customer> customers) {
        this.result = result;
        this.customers = customers;
    }
}

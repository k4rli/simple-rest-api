package com.karli.response.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.karli.customer.model.Customer;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomResponse {
    @NotNull
    String result;
    Customer customer;
    String error;

    public CustomResponse(String result) {
        this.result = result;
    }

    public CustomResponse(String result, Customer customer) {
        this.result = result;
        this.customer = customer;
    }

    public CustomResponse(@NotNull String result, String error) {
        this.result = result;
        this.error = error;
    }
}

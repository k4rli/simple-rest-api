package com.karli.customer.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class CustomerDTO {
    private static final int NAME_MIN_LENGTH = 2;
    private static final int GENERIC_FIELD_MIN_LENGTH = 1;
    private static final String CUSTOMER_DATE_FORMAT = "dd.MM.yyyy";

    @NotNull
    long id;

    @Size(
            min = NAME_MIN_LENGTH,
            message = "Name must be at least 2 characters long."
    )
    private String name;

    @Size(
            min = GENERIC_FIELD_MIN_LENGTH,
            message = "Address can't be empty."
    )
    private String address;

    private BigDecimal balance;

    @JsonFormat(pattern = CUSTOMER_DATE_FORMAT)
    private Date birthday;
}

package com.karli.customer.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity(name = "customers")
public class Customer {
    private static final int NAME_MIN_LENGTH = 2;
    private static final int GENERIC_FIELD_MIN_LENGTH = 1;
    private static final String CUSTOMER_DATE_FORMAT = "dd.MM.yyyy";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Size(min = NAME_MIN_LENGTH, message = "Name must be at least 2 characters long.")
    private String name;

    @Size(min = GENERIC_FIELD_MIN_LENGTH, message = "Address can't be empty.")
    private String address;

    private BigDecimal balance;

    @JsonFormat(pattern = CUSTOMER_DATE_FORMAT)
    private Date birthday;
}

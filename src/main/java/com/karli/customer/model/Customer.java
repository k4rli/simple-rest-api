package com.karli.customer.model;

import lombok.Builder;
import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity(name = "customers")
public class Customer {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @NotNull
    private String name;

    @NotNull
    private String address;

    @NotNull
    private BigDecimal balance;

    @NotNull
    private Date birthday;
}

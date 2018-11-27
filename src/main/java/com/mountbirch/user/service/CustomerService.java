package com.mountbirch.user.service;

import com.mountbirch.user.model.Customer;
import com.mountbirch.user.model.CustomerRegistrationFormDto;
import com.mountbirch.user.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CustomerService {

    private CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public boolean userExists(String email){
        Customer customer = customerRepository.findByPrimaryEmailIgnoreCase(email);
        return customer !=null;
    }

    public Customer createUserFromRegistrationForm(CustomerRegistrationFormDto customerRegistrationFormDto){
        Customer customerToRegister = new Customer();
        customerToRegister.setCreatedOn(LocalDateTime.now());
        customerToRegister.setPrimaryEmail(customerRegistrationFormDto.getEmail());
        customerToRegister.setEnabled(true);
        customerToRegister.setValidated(false);

        customerRepository.save(customerToRegister);

        return customerToRegister;
    }
}

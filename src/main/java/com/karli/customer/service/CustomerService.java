package com.karli.customer.service;

import com.karli.customer.model.Customer;
import com.karli.customer.repository.CustomerRepository;
import com.karli.exceptions.customer.CustomerNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Log4j2
@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private DateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");

    /**
     * Creates a new customer and saves to database.
     *
     * @param customer validated customer
     * @return created customer
     */
    public Customer createNewCustomer(Customer customer) {
        log.info("Creating new customer. Name: {}", customer.getName());
        return customerRepository.save(customer);
    }

    /**
     * @param id target customer's ID
     * @return found customer
     * @throws CustomerNotFoundException if can't find customer by ID
     */
    public Customer findCustomerByID(long id) throws CustomerNotFoundException {
        Customer targetCustomer = customerRepository.findById(id);
        if (targetCustomer == null) throw new CustomerNotFoundException(id);
        log.info("Found customer with required ID: {}", id);
        return targetCustomer;
    }

    /**
     * @param id target customer's ID
     * @throws CustomerNotFoundException if can't find customer by ID
     */
    public void deleteCustomerByID(long id) throws CustomerNotFoundException {
        long targetCustomer = customerRepository.deleteById(id);
        if (targetCustomer == 0) throw new CustomerNotFoundException(id);
        log.info("Customer with ID {} has been deleted.", id);
    }

    /**
     * @param customer   customer to modify
     * @param newAddress changed address
     * @return updated customer
     */
    private Customer updateCustomerAddress(Customer customer, String newAddress) {
        String oldAddress = customer.getAddress();
        customer.setAddress(newAddress);
        log.info("Updated address for customer with ID {}. Old address: {}, new address: {}.",
                customer.getId(), oldAddress, newAddress
        );
        return customer;
    }

    /**
     * @param customer customer to modify
     * @param value    balance
     * @return updated customer
     */
    private Customer updateCustomerBalance(Customer customer, String value) {
        BigDecimal newBalance = BigDecimal.valueOf(Double.parseDouble(value));
        BigDecimal oldBalance = customer.getBalance();
        customer.setBalance(newBalance);
        log.info("Updated balance for customer with ID {}. Old balance: {}, new balance: {}.",
                customer.getId(), oldBalance, newBalance
        );
        return customer;
    }

    /**
     * @param customer customer to modify
     * @param newName  changed name
     * @return updated customer
     */
    private Customer updateCustomerName(Customer customer, String newName) {
        String oldName = customer.getName();
        customer.setName(newName);
        log.info("Updated name for customer with ID {}. Old name: {}, new name: {}.",
                customer.getId(), oldName, newName
        );
        return customer;
    }

    /**
     * @param customer    customer to modify
     * @param newBirthday changed birthday
     * @return updated customer
     */
    private Customer updateCustomerBirthday(Customer customer, Date newBirthday) {
        Date oldBirthday = customer.getBirthday();
        customer.setBirthday(newBirthday);
        log.info("Updated birthday for customer with ID {}. Old birthday: {}, new birthday: {}.",
                customer.getId(), oldBirthday, newBirthday
        );
        return customer;
    }

    public Customer updateCustomer(Customer currentCustomer, Map<String, String> newValues) throws ParseException {
        for (String param : newValues.keySet()) {
            String value = newValues.get(param);
            if (param.equals("name")) updateCustomerName(currentCustomer, value);
            if (param.equals("address")) updateCustomerAddress(currentCustomer, value);
            if (param.equals("birthday")) updateCustomerBirthday(currentCustomer, simpleDateFormat.parse(value));
            if (param.equals("balance")) updateCustomerBalance(currentCustomer, value);
        }
        return customerRepository.save(currentCustomer);
    }

    public Page<Customer> getCustomers(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }
}

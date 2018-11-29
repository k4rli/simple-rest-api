package com.karli.customer.service;

import com.karli.customer.exceptions.customer.CustomerNotFoundException;
import com.karli.customer.model.Customer;
import com.karli.customer.repository.CustomerRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Slf4j
@Service
public class CustomerService {

    private CustomerRepository customerRepository;
    private DateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * Creates a new customer and saves to database.
     *
     * @param customer validated customer
     * @return created customer
     */
    public Customer createNewCustomer(Customer customer) {
        log.info("Creating new customer. Name: " + customer.getName());
        return this.customerRepository.save(customer);
    }

    /**
     * @param id target customer's ID
     * @return found customer
     * @throws CustomerNotFoundException if can't find customer by ID
     */
    public Customer findCustomerByID(long id) throws CustomerNotFoundException {
        Customer targetCustomer = this.customerRepository.findById(id);
        if (targetCustomer == null) throw new CustomerNotFoundException(id);
        log.info("Found customer with required ID: " + id);
        return targetCustomer;
    }

    /**
     * @param id target customer's ID
     * @throws CustomerNotFoundException if can't find customer by ID
     */
    public void deleteCustomerByID(long id) throws CustomerNotFoundException {
        long targetCustomer = this.customerRepository.deleteById(id);
        if (targetCustomer == 0) throw new CustomerNotFoundException(id);
        log.info("Customer with ID " + id + " has been deleted.");
    }

    /**
     * @param customer customer to modify
     * @param newAddress changed address
     * @return updated customer
     */
    private Customer updateCustomerAddress(Customer customer, String newAddress) {
        String oldAddress = customer.getAddress();
        customer.setAddress(newAddress);
        log.info("Updated address for customer with ID "+ customer.getId()
                + ". Old address: " + oldAddress
                + ", new address: " + newAddress + ".");
        return customer;
    }

    /**
     * @param customer customer to modify
     * @param newBalance changed balance
     * @return updated customer
     */
    private Customer updateCustomerBalance(Customer customer, BigDecimal newBalance) {
        BigDecimal oldBalance = customer.getBalance();
        customer.setBalance(newBalance);
        log.info("Updated balance for customer with ID "+ customer.getId()
                + ". Old balance: " + oldBalance.toString()
                + ", new balance: " + newBalance + ".");
        return customer;
    }

    /**
     * @param customer customer to modify
     * @param newName changed name
     * @return updated customer
     */
    private Customer updateCustomerName(Customer customer, String newName) {
        String oldName = customer.getName();
        customer.setName(newName);
        log.info("Updated name for customer with ID "+ customer.getId()
                + ". Old name: " + oldName
                + ", new name: " + newName + ".");
        return customer;
    }

    /**
     * @param customer customer to modify
     * @param newBirthday changed birthday
     * @return updated customer
     */
    private Customer updateCustomerBirthday(Customer customer, Date newBirthday) {
        Date oldBirthday = customer.getBirthday();
        customer.setBirthday(newBirthday);
        log.info("Updated birthday for customer with ID "+ customer.getId()
                + ". Old birthday: " + oldBirthday.toString()
                + ", new birthday: " + newBirthday.toString() + ".");
        return customer;
    }

    public Customer updateCustomer(Customer currentCustomer, Map<String, String> newValues) throws ParseException {
        for (String param : newValues.keySet()) {
            String value = newValues.get(param);
            if (param.equals("name")) {
                this.updateCustomerName(currentCustomer, value);
            }
            if (param.equals("address")) {
                this.updateCustomerAddress(currentCustomer, value);
            }
            if (param.equals("balance")) {
                BigDecimal newBalance = BigDecimal.valueOf(Double.parseDouble(value));
                this.updateCustomerBalance(currentCustomer, newBalance);
            }
            if (param.equals("birthday")) {
                this.updateCustomerBirthday(currentCustomer, simpleDateFormat.parse(value));
            }
        }
        return this.customerRepository.save(currentCustomer);
    }
}

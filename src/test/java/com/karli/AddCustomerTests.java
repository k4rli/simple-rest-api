package com.karli;

import org.junit.Test;
import org.junit.runner.RunWith;
import com.karli.customer.model.Customer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SuppressWarnings("Duplicates")
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AddCustomerTests {
    private ObjectMapper mapper = new ObjectMapper();
    private DateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
    private static final String ADD_CUSTOMER_ENDPOINT = "/customer/addCustomer";

    @Autowired
    private MockMvc mockMvc;

    public Customer createCustomer(
            String name, String address, BigDecimal balance, String birthday
    ) throws ParseException {
        Customer initialCustomer = new Customer();
        initialCustomer.setName(name);
        initialCustomer.setAddress(address);
        initialCustomer.setBalance(balance);
        initialCustomer.setBirthday(simpleDateFormat.parse(birthday));
        return initialCustomer;
    }

    @Test
    public void shouldAddCustomer() throws Exception {
        String initialCustomer = "{\n" +
                "  \"name\" : \"Jaanus\",\n" +
                "  \"address\" : \"Põhja 15\",\n" +
                "  \"balance\" : 58.05,\n" +
                "  \"birthday\" : \"12.01.1981\"\n" +
                "}";

        MockHttpServletRequestBuilder request = post(ADD_CUSTOMER_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(initialCustomer)
                .accept(MediaType.APPLICATION_JSON_UTF8);
        this.mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Jaanus"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value("Põhja 15"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.balance").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.birthday").isString())
                .andReturn();
    }

    @Test
    public void shouldFailAddCustomerIfNameMissing() throws Exception {
        String initialCustomer = "{\n" +
                "  \"address\" : \"Põhja 15\",\n" +
                "  \"balance\" : 58.05,\n" +
                "  \"birthday\" : \"12.01.1981\"\n" +
                "}";

        MockHttpServletRequestBuilder request = post(ADD_CUSTOMER_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(initialCustomer)
                .accept(MediaType.APPLICATION_JSON_UTF8);
        this.mockMvc
                .perform(request)
                .andExpect(status().is(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.field").value("addCustomer.customer.name"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("must not be null"))

                .andReturn();
    }
}

package com.karli;

import org.junit.Test;
import org.junit.runner.RunWith;
import com.karli.customer.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SuppressWarnings("Duplicates")
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CustomerOperationTests {
    private DateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
    private GsonJsonParser gsonJsonParser = new GsonJsonParser();

    private static final String ADD_CUSTOMER_ENDPOINT = "/customer/addCustomer";
    private static final String DELETE_CUSTOMER_ENDPOINT = "/customer/deleteCustomer";
    private static final String GET_CUSTOMER_ENDPOINT = "/customer/getCustomer";

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
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].field").value("addCustomer.customer.name"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].message").value("must not be null"))
                .andReturn();
    }

    @Test
    public void shouldDeleteCustomer() throws Exception {
        String initialCustomer = "{\n" +
                "  \"name\" : \"Jaanus\",\n" +
                "  \"address\" : \"Põhja 15\",\n" +
                "  \"balance\" : 58.05,\n" +
                "  \"birthday\" : \"12.01.1981\"\n" +
                "}";

        MockHttpServletRequestBuilder addCustomerRequest = post(ADD_CUSTOMER_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(initialCustomer)
                .accept(MediaType.APPLICATION_JSON_UTF8);
        MockHttpServletResponse addCustomerRequestResponse = this.mockMvc
                .perform(addCustomerRequest)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andReturn().getResponse();
        Map<String, Object> mappedResults = gsonJsonParser.parseMap(addCustomerRequestResponse.getContentAsString());
        long customerID = Math.round((double) mappedResults.get("id"));

        MockHttpServletRequestBuilder deleteCustomerRequest = delete(DELETE_CUSTOMER_ENDPOINT)
                .param("id", Long.toString(customerID, 10));
        this.mockMvc
                .perform(deleteCustomerRequest)
                .andExpect(status().isOk())
                .andExpect(content().string("Customer with ID " + customerID + " has been deleted."))
                .andReturn();
    }

    @Test
    public void shouldGetCustomer() throws Exception {
        String initialCustomer = "{\n" +
                "  \"name\" : \"Jaanus\",\n" +
                "  \"address\" : \"Põhja 16\",\n" +
                "  \"balance\" : 58.05,\n" +
                "  \"birthday\" : \"12.01.1981\"\n" +
                "}";

        MockHttpServletRequestBuilder addCustomerRequest = post(ADD_CUSTOMER_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(initialCustomer)
                .accept(MediaType.APPLICATION_JSON_UTF8);
        MockHttpServletResponse addCustomerRequestResponse = this.mockMvc
                .perform(addCustomerRequest)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andReturn().getResponse();
        Map<String, Object> mappedResults = gsonJsonParser.parseMap(addCustomerRequestResponse.getContentAsString());
        long customerID = Math.round((double) mappedResults.get("id"));

        MockHttpServletRequestBuilder getCustomerRequest = get(GET_CUSTOMER_ENDPOINT)
                .param("id", Long.toString(customerID, 10));
        this.mockMvc
                .perform(getCustomerRequest)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Jaanus"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value("Põhja 16"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.balance").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.birthday").isString())
                .andReturn();
    }

    @Test
    public void shouldGetCustomerWhoDoesNotExist() throws Exception {
        MockHttpServletRequestBuilder getCustomerRequest = get(GET_CUSTOMER_ENDPOINT)
                .param("id", "2");
        this.mockMvc
                .perform(getCustomerRequest)
                .andExpect(status().is(204))
                .andExpect(MockMvcResultMatchers.jsonPath("*.error").value("Could not find customer by ID: 3"))
                .andDo(print())
                .andReturn();
    }
}

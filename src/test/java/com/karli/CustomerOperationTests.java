package com.karli;

import com.karli.response.types.CustomResponseCodes;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
    private static final String MODIFY_CUSTOMER_ENDPOINT = "/customer/modifyCustomer";

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
        String initialCustomer = "{" +
                "  \"name\" : \"Jaanus\"," +
                "  \"address\" : \"Põhja 15\"," +
                "  \"balance\" : 58.05," +
                "  \"birthday\" : \"12.01.1981\"" +
                "}";

        MockHttpServletRequestBuilder request = post(ADD_CUSTOMER_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(initialCustomer)
                .accept(MediaType.APPLICATION_JSON_UTF8);
        this.mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(CustomResponseCodes.SUCCESS.getCode()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.customer.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.customer.name").value("Jaanus"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.customer.address").value("Põhja 15"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.customer.balance").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.customer.birthday").isString())
                .andReturn();
    }

    @Test
    public void shouldFailAddCustomerIfNameMissing() throws Exception {
        String initialCustomer = "{" +
                "  \"address\" : \"Põhja 15\"," +
                "  \"balance\" : 58.05," +
                "  \"birthday\" : \"12.01.1981\"" +
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
        String initialCustomer = "{" +
                "  \"name\" : \"Jaanus\"," +
                "  \"address\" : \"Põhja 15\"," +
                "  \"balance\" : 58.05," +
                "  \"birthday\" : \"12.01.1981\"" +
                "}";

        MockHttpServletRequestBuilder addCustomerRequest = post(ADD_CUSTOMER_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(initialCustomer)
                .accept(MediaType.APPLICATION_JSON_UTF8);
        MockHttpServletResponse addCustomerRequestResponse = this.mockMvc
                .perform(addCustomerRequest)
                .andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.customer.id").isNumber())
                .andReturn().getResponse();
        Map<String, Object> mappedResults = gsonJsonParser.parseMap(addCustomerRequestResponse.getContentAsString());
        Map<String, Object> customerDataResults = (Map<String, Object>) mappedResults.get("customer");
        long customerID = Math.round((double) customerDataResults.get("id"));

        MockHttpServletRequestBuilder deleteCustomerRequest = delete(DELETE_CUSTOMER_ENDPOINT)
                .param("id", Long.toString(customerID, 10));
        this.mockMvc
                .perform(deleteCustomerRequest)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(CustomResponseCodes.SUCCESS.getCode()))
                .andReturn();
    }

    @Test
    public void attemptToDeleteNonexistentCustomer() throws Exception {
        MockHttpServletRequestBuilder deleteCustomerRequest = delete(DELETE_CUSTOMER_ENDPOINT)
                .param("id", "7457314");
        this.mockMvc
                .perform(deleteCustomerRequest)
                .andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(CustomResponseCodes.ERROR.getCode()))
                .andReturn();
    }

    @Test
    public void shouldGetCustomer() throws Exception {
        String initialCustomer = "{" +
                "  \"name\" : \"Jaanus\"," +
                "  \"address\" : \"Põhja 16\"," +
                "  \"balance\" : 58.05," +
                "  \"birthday\" : \"12.01.1981\"" +
                "}";

        MockHttpServletResponse addCustomerRequestResponse = this.mockMvc
                .perform(post(ADD_CUSTOMER_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(initialCustomer)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.customer.id").isNumber())
                .andReturn().getResponse();
        Map<String, Object> mappedResults = gsonJsonParser.parseMap(addCustomerRequestResponse.getContentAsString());
        Map<String, Object> customerDataResults = (Map<String, Object>) mappedResults.get("customer");
        long customerID = Math.round((double) customerDataResults.get("id"));

        MockHttpServletRequestBuilder getCustomerRequest = get(GET_CUSTOMER_ENDPOINT)
                .param("id", Long.toString(customerID, 10));
        this.mockMvc
                .perform(getCustomerRequest)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(CustomResponseCodes.SUCCESS.getCode()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.customer.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.customer.name").value("Jaanus"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.customer.address").value("Põhja 16"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.customer.balance").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.customer.birthday").isString())
                .andReturn();
    }

    @Test
    public void shouldGetCustomerWhoDoesNotExist() throws Exception {
        String customerID = Long.toString(45745745783L);
        MockHttpServletRequestBuilder getCustomerRequest = get(GET_CUSTOMER_ENDPOINT)
                .param("id", customerID);
        this.mockMvc
                .perform(getCustomerRequest)
                .andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result")
                        .value(CustomResponseCodes.ERROR.getCode()))
                .andReturn();
    }

    @Test
    public void modifyCustomerSuccessfully() throws Exception {
        String initialCustomer = "{" +
                "  \"name\" : \"Jaanus\"," +
                "  \"address\" : \"Põhja 15\"," +
                "  \"balance\" : 58.05," +
                "  \"birthday\" : \"12.01.1981\"" +
                "}";
        Map<String, Object> initialCustomerAsJSON = gsonJsonParser.parseMap(initialCustomer);
        MockHttpServletResponse addCustomerRequestResponse = this.mockMvc
                .perform(post(ADD_CUSTOMER_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(initialCustomer)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.customer.id").isNumber())
                .andReturn().getResponse();

        Map<String, Object> mappedResults = gsonJsonParser.parseMap(addCustomerRequestResponse.getContentAsString());
        Map<String, Object> customerDataResults = (Map<String, Object>) mappedResults.get("customer");
        long customerID = Math.round((double) customerDataResults.get("id"));

        MockHttpServletRequestBuilder modifyCustomerRequest = put(MODIFY_CUSTOMER_ENDPOINT)
                .param("id", Long.toString(customerID, 10))
                .param("address", "51 ajhõP");
        this.mockMvc
                .perform(modifyCustomerRequest)
                .andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(CustomResponseCodes.SUCCESS.getCode()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.customer.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.customer.name").value(initialCustomerAsJSON.get("name")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.customer.address").value("51 ajhõP"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.customer.balance").value(initialCustomerAsJSON.get("balance")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.customer.birthday").isString())
                .andReturn();
    }

    @Test
    public void attemptToModifyNonexistentCustomer() throws Exception {
        MockHttpServletRequestBuilder modifyCustomerRequest = put(MODIFY_CUSTOMER_ENDPOINT)
                .param("id", "6436346878")
                .param("address", "51 ajhõP");
        this.mockMvc
                .perform(modifyCustomerRequest)
                .andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(CustomResponseCodes.ERROR.getCode()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.customer").doesNotExist())
                .andDo(print())
                .andReturn();
    }
}

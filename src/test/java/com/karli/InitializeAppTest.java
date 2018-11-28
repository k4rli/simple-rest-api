package com.karli;

import com.karli.customer.model.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class InitializeAppTest {
    @Autowired
    private MockMvc mockMvc;

    Customer customer = new Customer();

    @Test
    public void baseURLResponds() throws Exception {
        MockHttpServletRequestBuilder request = get("/");
        this.mockMvc
                .perform(request)
                .andExpect(status().is(200))
                .andExpect(content().string(containsString("Hello world")));
    }

    @Test
    public void pingResponds() throws Exception {
        this.mockMvc
                .perform(get("/ping"))
                .andExpect(status().is(200));
    }
}

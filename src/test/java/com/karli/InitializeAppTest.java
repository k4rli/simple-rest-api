package com.karli;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

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

    @Test
    public void testEndpointRespondsWithValidJson() throws Exception {
        this.mockMvc
                .perform(get("/test"))
                .andExpect(status().is(200))
                .andExpect(content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]").value("Hello"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1]").value("world"));
    }
}

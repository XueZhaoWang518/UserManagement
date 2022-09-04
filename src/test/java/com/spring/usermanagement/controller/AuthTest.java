package com.spring.usermanagement.controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.usermanagement.payload.request.SigninRequest;
import com.spring.usermanagement.payload.request.SignupRequest;
import com.spring.usermanagement.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import static org.mockito.ArgumentMatchers.contains;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class AuthTest {
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new AuthController()).build();
    }

    public String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    protected <T> T mapFromJson(String json, Class<T> clazz) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }


    @Test
    public void testSignup() throws Exception {
        SignupRequest user = new SignupRequest("liu", "liu@123.com", "12345678");
        String inputJson = mapToJson(user);
        try {
            MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/signup")
                            .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
                    .andExpect(MockMvcResultMatchers.status().is(200))
                    .andExpect(content().string(contains("User registered successfully")))
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testSignin() throws Exception {
        SigninRequest user = new SigninRequest("liu", "12345678");
        String inputJson = mapToJson(user);
        try {
            MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/signin")
                            .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
                    .andExpect(MockMvcResultMatchers.status().is(200))
                    .andExpect(content().string(contains("username")))
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

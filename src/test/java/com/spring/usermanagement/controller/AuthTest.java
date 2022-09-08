package com.spring.usermanagement.controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.usermanagement.payload.request.SigninRequest;
import com.spring.usermanagement.payload.request.SignupRequest;
import com.spring.usermanagement.payload.response.JwtResponse;
import com.spring.usermanagement.payload.response.MessageResponse;
import com.spring.usermanagement.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AuthTest {
    @Resource
    MockMvc mockMvc;

    @MockBean
    public UserService userService;


    public String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }


    @Test
    public void testSignup() throws Exception {
        when(userService.addUser("liu", "liu@123.com", "12345678")).thenReturn(ResponseEntity.ok(new MessageResponse("User registered successfully!")));
        SignupRequest user = new SignupRequest("liu", "liu@123.com", "12345678");
        String inputJson = mapToJson(user);
        try {
            MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/signup")
                            .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
                    .andExpect(MockMvcResultMatchers.status().is(200))
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn();
            String signUpRequestBody = mvcResult.getResponse().getContentAsString();
            String expectedRequestBody = "{\"message\":\"User registered successfully!\"}";
            assertEquals(expectedRequestBody, signUpRequestBody);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testSignin() throws Exception {
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJsaW55dW1lbmciLCJpYXQiOjE2NjIxOTgzMjUsImV4cCI6MTY2MjI4NDcyNX0.kHmTBGBm1jd1jylDwLUyMaXyPXG7h-1_jcs2mdhViWUAy0QZQrkuKrpWnYdvLUo1FAAoyOIvMOyBDEDZ-YtUOw";
        List<String> roles = new ArrayList<String>();
        roles.add("ROLE_USER");
        when(userService.signIn("liu", "12345678"))
                .thenReturn(ResponseEntity.ok(new JwtResponse(token,
                        1L,
                        "liu",
                        "liu@123.com",
                        roles
                )));
        SigninRequest user = new SigninRequest("liu", "12345678");
        String inputJson = mapToJson(user);
        try {
            MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/signin")
                            .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
                    .andExpect(MockMvcResultMatchers.status().is(200))
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn();
            String actualRequestBody = mvcResult.getResponse().getContentAsString();
            assertThat(actualRequestBody.contains("token"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}


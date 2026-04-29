package com.aws.signup.signup.controller;

import com.aws.signup.signup.model.User;
import com.aws.signup.signup.service.SignupService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("SignupController Tests")
class SignupControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SignupService signupService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setEmail("test@example.com");
        testUser.setPassword("hashedPassword123");
    }

    @Test
    @DisplayName("Should successfully signup a new user")
    void testSignupSuccess() throws Exception {
        User newUser = new User();
        newUser.setEmail("newuser@example.com");
        newUser.setPassword("password123");

        when(signupService.registerUser(any(User.class))).thenReturn(testUser);

        mockMvc.perform(post("/api/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    @DisplayName("Should return bad request when signup fails")
    void testSignupFailure() throws Exception {
        User newUser = new User();
        newUser.setEmail("existing@example.com");
        newUser.setPassword("password123");

        when(signupService.registerUser(any(User.class)))
                .thenThrow(new RuntimeException("User already exists with email: existing@example.com"));

        mockMvc.perform(post("/api/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should retrieve all users")
    void testGetAllUsers() throws Exception {
        User user1 = new User();
        user1.setId(1L);
        user1.setEmail("user1@example.com");
        user1.setPassword("hashedPassword1");

        User user2 = new User();
        user2.setId(2L);
        user2.setEmail("user2@example.com");
        user2.setPassword("hashedPassword2");

        List<User> users = List.of(user1, user2);

        when(signupService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/api/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].email").value("user1@example.com"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].email").value("user2@example.com"));
    }

    @Test
    @DisplayName("Should return empty list when no users exist")
    void testGetAllUsersEmpty() throws Exception {
        when(signupService.getAllUsers()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }
}


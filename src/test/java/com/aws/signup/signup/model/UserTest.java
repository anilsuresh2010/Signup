package com.aws.signup.signup.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("User Model Tests")
class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @Test
    @DisplayName("Should create user with all fields")
    void testUserCreation() {
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setPassword("password123");

        assertNotNull(user);
        assertEquals(1L, user.getId());
        assertEquals("test@example.com", user.getEmail());
        assertEquals("password123", user.getPassword());
    }

    @Test
    @DisplayName("Should set and get user id")
    void testSetGetId() {
        user.setId(5L);

        assertEquals(5L, user.getId());
    }

    @Test
    @DisplayName("Should set and get user email")
    void testSetGetEmail() {
        user.setEmail("user@example.com");

        assertEquals("user@example.com", user.getEmail());
    }

    @Test
    @DisplayName("Should set and get user password")
    void testSetGetPassword() {
        user.setPassword("securePassword");

        assertEquals("securePassword", user.getPassword());
    }

    @Test
    @DisplayName("Should update user email")
    void testUpdateEmail() {
        user.setEmail("old@example.com");
        user.setEmail("new@example.com");

        assertEquals("new@example.com", user.getEmail());
    }

    @Test
    @DisplayName("Should update user password")
    void testUpdatePassword() {
        user.setPassword("oldPassword");
        user.setPassword("newPassword");

        assertEquals("newPassword", user.getPassword());
    }

    @Test
    @DisplayName("Should handle null values")
    void testNullValues() {
        user.setEmail(null);
        user.setPassword(null);

        assertNull(user.getEmail());
        assertNull(user.getPassword());
    }

    @Test
    @DisplayName("Should handle empty strings")
    void testEmptyStrings() {
        user.setEmail("");
        user.setPassword("");

        assertEquals("", user.getEmail());
        assertEquals("", user.getPassword());
    }
}


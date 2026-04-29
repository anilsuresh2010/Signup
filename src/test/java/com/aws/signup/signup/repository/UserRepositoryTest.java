package com.aws.signup.signup.repository;

import com.aws.signup.signup.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("UserRepository Tests")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setEmail("test@example.com");
        testUser.setPassword("password123");
    }

    @Test
    @DisplayName("Should save a new user to database")
    void testSaveUser() {
        User savedUser = userRepository.save(testUser);

        assertNotNull(savedUser);
        assertNotNull(savedUser.getId());
        assertEquals("test@example.com", savedUser.getEmail());
        assertEquals("password123", savedUser.getPassword());
    }

    @Test
    @DisplayName("Should retrieve user by ID")
    void testFindUserById() {
        User savedUser = userRepository.save(testUser);

        Optional<User> foundUser = userRepository.findById(savedUser.getId());

        assertTrue(foundUser.isPresent());
        assertEquals("test@example.com", foundUser.get().getEmail());
    }

    @Test
    @DisplayName("Should return true when user exists by email")
    void testExistsByEmailTrue() {
        userRepository.save(testUser);

        boolean exists = userRepository.existsByEmail("test@example.com");

        assertTrue(exists);
    }

    @Test
    @DisplayName("Should return false when user does not exist by email")
    void testExistsByEmailFalse() {
        boolean exists = userRepository.existsByEmail("nonexistent@example.com");

        assertFalse(exists);
    }

    @Test
    @DisplayName("Should retrieve all users from database")
    void testFindAllUsers() {
        User user1 = new User();
        user1.setEmail("user1@example.com");
        user1.setPassword("password1");

        User user2 = new User();
        user2.setEmail("user2@example.com");
        user2.setPassword("password2");

        userRepository.save(user1);
        userRepository.save(user2);

        List<User> users = userRepository.findAll();

        assertNotNull(users);
        assertEquals(2, users.size());
    }

    @Test
    @DisplayName("Should delete user from database")
    void testDeleteUser() {
        User savedUser = userRepository.save(testUser);
        Long userId = savedUser.getId();

        userRepository.deleteById(userId);

        Optional<User> deletedUser = userRepository.findById(userId);

        assertTrue(deletedUser.isEmpty());
    }

    @Test
    @DisplayName("Should update user email")
    void testUpdateUser() {
        User savedUser = userRepository.save(testUser);
        savedUser.setEmail("updated@example.com");

        User updatedUser = userRepository.save(savedUser);

        assertEquals("updated@example.com", updatedUser.getEmail());
    }
}


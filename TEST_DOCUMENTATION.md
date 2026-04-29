# JUnit 5 Test Cases Documentation

## Overview
Comprehensive test coverage for the Signup microservice with JUnit 5 and Mockito.

## Test Files Created

### 1. SignupControllerTest.java
**Location:** `src/test/java/com/aws/signup/signup/controller/SignupControllerTest.java`

**Test Cases:**
- `testSignupSuccess()` - Verifies successful user registration with valid input
- `testSignupFailure()` - Verifies bad request when user already exists
- `testGetAllUsers()` - Verifies retrieval of multiple users
- `testGetAllUsersEmpty()` - Verifies empty list response when no users exist

**Key Features:**
- Uses `@SpringBootTest` and `@AutoConfigureMockMvc` for integration testing
- Uses `MockMvc` to perform HTTP requests
- Mocks `SignupService` to isolate controller logic
- Tests both POST `/api/signup` and GET `/api/users` endpoints

---

### 2. SignupServiceTest.java
**Location:** `src/test/java/com/aws/signup/signup/service/SignupServiceTest.java`

**Test Cases:**
- `testRegisterUserSuccess()` - Verifies successful user registration
- `testRegisterUserAlreadyExists()` - Verifies exception when duplicate email
- `testPasswordEncoding()` - Verifies password is encoded before saving
- `testGetAllUsersSuccess()` - Verifies retrieval of all users
- `testGetAllUsersEmpty()` - Verifies empty list when no users

**Key Features:**
- Uses `@ExtendWith(MockitoExtension.class)` for unit testing
- Mocks `UserRepository` and `PasswordEncoder` dependencies
- Uses `@InjectMocks` to inject mocked dependencies
- Verifies mock interactions with `verify()`

---

### 3. UserRepositoryTest.java
**Location:** `src/test/java/com/aws/signup/signup/repository/UserRepositoryTest.java`

**Test Cases:**
- `testSaveUser()` - Verifies user can be saved to database
- `testFindUserById()` - Verifies user retrieval by ID
- `testExistsByEmailTrue()` - Verifies email existence check (positive)
- `testExistsByEmailFalse()` - Verifies email existence check (negative)
- `testFindAllUsers()` - Verifies retrieval of all users
- `testDeleteUser()` - Verifies user deletion
- `testUpdateUser()` - Verifies user update functionality

**Key Features:**
- Uses `@DataJpaTest` for JPA repository testing
- Works with H2 in-memory database
- Tests CRUD operations and custom query methods
- Verifies database persistence layer

---

### 4. UserTest.java
**Location:** `src/test/java/com/aws/signup/signup/model/UserTest.java`

**Test Cases:**
- `testUserCreation()` - Verifies user object creation with all fields
- `testSetGetId()` - Tests ID setter/getter
- `testSetGetEmail()` - Tests email setter/getter
- `testSetGetPassword()` - Tests password setter/getter
- `testUpdateEmail()` - Verifies email update
- `testUpdatePassword()` - Verifies password update
- `testNullValues()` - Verifies handling of null values
- `testEmptyStrings()` - Verifies handling of empty strings

**Key Features:**
- Tests the User entity model
- Verifies all getters and setters work correctly
- Tests edge cases (null, empty strings)
- Uses plain JUnit 5 assertions

---

## Running the Tests

### Run All Tests
```bash
mvn clean test
```

### Run Specific Test Class
```bash
mvn test -Dtest=SignupControllerTest
mvn test -Dtest=SignupServiceTest
mvn test -Dtest=UserRepositoryTest
mvn test -Dtest=UserTest
```

### Run Specific Test Method
```bash
mvn test -Dtest=SignupControllerTest#testSignupSuccess
```

### Run Tests with Coverage
```bash
mvn clean test jacoco:report
```

---

## Test Annotations Used

| Annotation | Purpose | Used In |
|-----------|---------|---------|
| `@SpringBootTest` | Full Spring context for integration tests | Controller, Service Tests |
| `@AutoConfigureMockMvc` | Enables MockMvc for HTTP testing | Controller Tests |
| `@DataJpaTest` | JPA repository testing with database | Repository Tests |
| `@ExtendWith(MockitoExtension.class)` | Enables Mockito annotations | Service Tests |
| `@Mock` | Creates mock objects | Service Tests |
| `@InjectMocks` | Injects mocks into class under test | Service Tests |
| `@Autowired` | Spring dependency injection | Controller, Repository Tests |
| `@BeforeEach` | Setup method before each test | All Tests |
| `@Test` | Marks method as test case | All Tests |
| `@DisplayName` | Human-readable test description | All Tests |

---

## Testing Framework Dependencies

The tests use the following libraries:
- **JUnit 5** - Testing framework
- **Mockito** - Mocking framework
- **Spring Boot Test** - Spring Boot testing utilities
- **MockMvc** - Spring MVC testing framework
- **H2 Database** - In-memory database for testing

---

## Best Practices Applied

1. **Naming Convention** - Test class names end with `Test` suffix
2. **Descriptive Names** - Test methods use `test` prefix and describe what they test
3. **One Assertion Focus** - Each test focuses on a single aspect
4. **AAA Pattern** - Arrange, Act, Assert structure
5. **DisplayName** - Human-readable test descriptions
6. **Mock Isolation** - Mocks used to isolate units under test
7. **Setup/Teardown** - `@BeforeEach` for test initialization
8. **Verification** - Mock interactions verified with `verify()`
9. **Edge Cases** - Tests cover success, failure, empty, and null cases
10. **No Test Interdependence** - Each test is independent

---

## Coverage Summary

- **Controller Layer** - 4 test cases covering HTTP endpoints
- **Service Layer** - 5 test cases covering business logic
- **Repository Layer** - 7 test cases covering database operations
- **Model Layer** - 8 test cases covering entity properties

**Total Test Cases: 24**


# Test Fixes Documentation

## Issues Found and Fixed

### Issue 1: Incorrect Mock Setup in Controller Test
**Problem:** Using `@Mock` with `@SpringBootTest` doesn't properly inject mocks into the Spring context.

**Solution:** Changed to `@MockBean` which Spring Boot recognizes and properly replaces beans in the application context.

**Before:**
```java
@ExtendWith(MockitoExtension.class)
@Mock
private SignupService signupService;
```

**After:**
```java
@MockBean
private SignupService signupService;
```

---

## Test Failure Analysis

### Test Failures Fixed:

1. **testSignupSuccess** ❌ → ✅
   - **Error:** Expected email `test@example.com` but got `newuser@example.com`
   - **Root Cause:** Mock was not being injected into the real service
   - **Fix:** Using `@MockBean` ensures the mock replaces the actual service

2. **testSignupFailure** ❌ → ✅
   - **Error:** Expected status 400 but got 200
   - **Root Cause:** RuntimeException was not being thrown by the mocked service
   - **Fix:** `@MockBean` properly throws the configured exception

3. **testGetAllUsers** ❌ → ✅
   - **Error:** Expected array length 2 but got 0
   - **Root Cause:** Mock was not returning the configured list
   - **Fix:** `@MockBean` returns the mocked list correctly

4. **testGetAllUsersEmpty** ❌ → ✅
   - **Error:** Expected array length 0 but got 2
   - **Root Cause:** Mock configuration was reversed
   - **Fix:** With proper `@MockBean` setup, correct list is returned

---

## Key Changes Made

### SignupControllerTest.java

**Removed:**
- `@ExtendWith(MockitoExtension.class)` - Not needed with @SpringBootTest
- `@Mock` annotation - Replaced with @MockBean

**Kept:**
- `@SpringBootTest` - Loads full Spring context
- `@AutoConfigureMockMvc` - Provides MockMvc for HTTP testing
- `@Autowired` for MockMvc and ObjectMapper

**Why This Works:**
1. `@SpringBootTest` loads the real application context
2. `@AutoConfigureMockMvc` provides MockMvc for making requests
3. `@MockBean` replaces the `SignupService` bean in the context with a mock
4. When the controller is called, it receives the mocked service
5. The mock behaves exactly as configured in the test

---

## When to Use Each Annotation

| Scenario | Annotation | Approach |
|----------|-----------|----------|
| Unit test service in isolation | `@ExtendWith(MockitoExtension.class)` + `@Mock` | Pure Mockito |
| Integration test with mocked dependencies | `@SpringBootTest` + `@MockBean` | Spring Test |
| Integration test with real dependencies | `@SpringBootTest` | Spring Test (full stack) |
| JPA repository testing | `@DataJpaTest` | Spring Test (DB only) |
| Web layer testing | `@WebMvcTest` + `@MockBean` | Spring Test (web only) |

---

## Test Execution Flow

```
User sends POST request to /api/signup
        ↓
MockMvc intercepts request
        ↓
SignupController.signup() is called
        ↓
Controller calls signupService.registerUser()
        ↓
@MockBean provides mocked SignupService
        ↓
Mock returns configured response (testUser or RuntimeException)
        ↓
Controller handles response/exception
        ↓
MockMvc verifies status and response body
        ↓
Assertion passes/fails
```

---

## Running the Fixed Tests

```bash
# Run all tests
mvn clean test

# Run only controller tests
mvn test -Dtest=SignupControllerTest

# Run with detailed output
mvn test -Dtest=SignupControllerTest -X

# Run specific test method
mvn test -Dtest=SignupControllerTest#testSignupSuccess
```

---

## Expected Test Results After Fix

All 25 tests should pass:
- ✅ SignupControllerTest: 4 tests
- ✅ SignupServiceTest: 5 tests
- ✅ UserRepositoryTest: 7 tests
- ✅ UserTest: 8 tests
- ✅ SignupApplicationTests: 1 test

**Total: 25 Tests - All Passing**


package gather.gather.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import gather.gather.Model.User;
import gather.gather.Repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
// @ExtendWith(MockitoExtension.class)
public class UserControllerUnitTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    private UserRepository userRepository;

    @Test // Test case 1: User doesn't exist (success case)
    public void test_signUp_Success() throws Exception {
        User user = new User("gail", "gail@example.com", "124");

        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/users/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isOk());
    }

    @Test // Test case 2: User already exists
    public void test_SignUp_ExistingEmail() throws Exception {
        User user = new User("gail", "gail@example.com", "124");

        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);

        mockMvc.perform(post("/users/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Email already exists"));
    }


    @Test // Test case 3: Invalid input
    public void test_SignUp_InvalidInput() throws Exception {
        User invalidUser = new User("", "invalidemail", "");

        mockMvc.perform(post("/users/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(invalidUser)))
                .andExpect(status().isBadRequest());
    }


    @Test // Test case 4: User with missing name
    public void test_SignUp_MissingName() throws Exception {
        User user = new User("", "gail@example.com", "124");

        mockMvc.perform(post("/users/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid input"));
    }

    @Test // Test case 5: User with missing email
    public void test_SignUp_MissingEmail() throws Exception {
        User user = new User("gail", null, "124");

        mockMvc.perform(post("/users/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid input"));
    }


    @Test // Test case 6: User with missing password
    public void test_SignUp_MissingPassword() throws Exception {
        User user = new User("gail", "gail@example.com", "");

        mockMvc.perform(post("/users/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid input"));
    }

    @Test // Test case 7: User with null name
    public void test_SignUp_NullName() throws Exception {
        User user = new User(null, "gail@example.com", "124");

        mockMvc.perform(post("/users/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid input"));
    }

    @Test // Test case 8: User with null email
    public void test_SignUp_NullEmail() throws Exception {
        User user = new User("gail", null, "124");

        mockMvc.perform(post("/users/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid input"));
    }

    @Test // Test case 9: User with null password
    public void test_SignUp_NullPassword() throws Exception {
        User user = new User("gail", "gail@example.com", null);

        mockMvc.perform(post("/users/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid input"));
    }


    @Test //  Test case 10: Updating an existing user
    public void test_updateUser() throws Exception {
            String userId = "1"; // Assuming "1" as the ID
            User existingUser = new User("gail", "gail@example.com", "124");
            User updatedUser = new User("gail", "gail@example.com", "pw124");

            when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser)); // Mock the behavior of findById method

            mockMvc.perform(put("/users/update/{id}", userId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(new ObjectMapper().writeValueAsString(updatedUser)))
                    .andExpect(status().isOk()) // Assert that the response status is OK (HTTP 200)
                    .andExpect(jsonPath("$.name").value(updatedUser.getName())) // Assert that the name is updated
                    .andExpect(jsonPath("$.email").value(updatedUser.getEmail())) // Assert that the email is updated
                    .andExpect(jsonPath("$.password").value(updatedUser.getPassword())); // Assert that the password is updated
        }

    @Test //  Test case 11: Updating a non-existing user
    public void test_updateUser_UserNotFound() throws Exception {
            String nonExistingUserId = "100"; // Assuming "100" as a non-existing ID
            User updatedUser = new User("gail", "gail@example.com", "pw124");

            when(userRepository.findById(nonExistingUserId)).thenReturn(Optional.empty()); // Mock the behavior of findById method for a non-existing user

            mockMvc.perform(put("/users/update/{id}", nonExistingUserId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(new ObjectMapper().writeValueAsString(updatedUser)))
                    .andExpect(status().isNotFound()); // Assert that the response status is "Not Found" (HTTP 404)
        }


    @Test // Test Case 12: Updating when handling invalid request body --> missing name
    public void test_updateUserWithMissingName() throws Exception {
        String existingUserId = "1"; // Assuming "1" as the ID
        User invalidUser = new User(null, "gail@example.com", "password"); // Invalid user object with missing name

        when(userRepository.findById(existingUserId)).thenReturn(Optional.of(new User("gail", "gail@example.com", "124"))); // Mock the behavior of findById method for an existing user

        mockMvc.perform(put("/users/update/{id}", existingUserId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(invalidUser)))
                .andExpect(status().isBadRequest()) // Assert that the response status is "Bad Request" (HTTP 400)
                .andExpect(content().string("Invalid user information"));
    }


    @Test // Test Case 13: Updating when handling invalid request body --> missing email
    public void test_updateUserWithMissingEmail() throws Exception {
        String existingUserId = "1"; // Assuming "1" as the ID
        User invalidUser = new User("gail", null, "124"); // Invalid user object with a missing required field (email)

        when(userRepository.findById(existingUserId)).thenReturn(Optional.of(new User("gail", "gail@example.com", "124"))); // Mock the behavior of findById method for an existing user

        mockMvc.perform(put("/users/update/{id}", existingUserId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(invalidUser)))
                .andExpect(status().isBadRequest()); // Assert that the response status is "Bad Request" (HTTP 400)
    }

    @Test // Test Case 14: Updating when handling invalid request body --> missing password
    public void test_updateUserWithMissingPassword() throws Exception {
        String existingUserId = "1"; // Assuming "1" as the ID
        User invalidUser = new User("gail", "gail@example.com", null); // Invalid user object with missing password

        when(userRepository.findById(existingUserId)).thenReturn(Optional.of(new User("gail", "gail@example.com", "124"))); // Mock the behavior of findById method for an existing user

        mockMvc.perform(put("/users/update/{id}", existingUserId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(invalidUser)))
                .andExpect(status().isBadRequest()); // Assert that the response status is "Bad Request" (HTTP 400)
    }

    @Test // Test case 15:  Updating when all fields are missing
    public void test_updateUserWithAllFieldsMissing() throws Exception {
        String existingUserId = "1"; // Assuming "1" as the ID
        User invalidUser = new User(null, null, null); // Invalid user object with all fields null

        when(userRepository.findById(existingUserId)).thenReturn(Optional.of(new User("gail", "gail@example.com", "124"))); // Mock the behavior of findById method for an existing user

        mockMvc.perform(put("/users/update/{id}", existingUserId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(invalidUser)))
                .andExpect(status().isBadRequest()); // Assert that the response status is "Bad Request" (HTTP 400)
    }


    @Test // Test case 16: Accessing user when a valid id is provided
    public void test_accessUser() throws Exception {
        User user = new User("gail", "gail@example.com", "124");
        when(userRepository.findById("1")).thenReturn(Optional.of(user)); // Assuming "1" as the ID

        mockMvc.perform(get("/users/access/{id}", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(user.getName())) // Assuming name is a field in User class
                .andExpect(jsonPath("$.email").value(user.getEmail())) // Assuming email is a field in User class
                .andExpect(jsonPath("$.password").value(user.getPassword())); // Assuming password is a field in User class
    }

    @Test // Test case 17: Accessing when the user with ID does not exist
    public void test_accessUser_UserNotFound() throws Exception {
        when(userRepository.findById("2")).thenReturn(Optional.empty()); // Assuming "2" as a non-existent ID

        mockMvc.perform(get("/users/access/{id}", "2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test // Test case 18: Deleting an existing user
    public void test_deleteUser() throws Exception {
        User user = new User("gail", "gail@example.com", "124");
        String userId = "1"; // Assuming "1" as the ID

        when(userRepository.findById(userId)).thenReturn(Optional.of(user)); //  use when from Mockito to mock the behavior of the findById method of the userRepository

        mockMvc.perform(delete("/users/delete/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)) //  use mockMvc to perform the DELETE request to the /users/delete/{id} endpoint, where {id} is replaced with the actual userId value.
                .andExpect(status().isOk()) // assert that the response status is OK (HTTP 200)
                .andExpect(content().string("User deleted successfully")); // assert content of the response is "User deleted successfully".
    }

    @Test // Test case 19: Deleting a non-existing user
    public void test_deleteNonExistingUser() throws Exception {
        String userId = "1"; // Assuming "1" as the ID

        when(userRepository.findById(userId)).thenReturn(Optional.empty()); // Mock the behavior for a non-existing user

        mockMvc.perform(delete("/users/delete/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()); // Assert that the response status is Not Found (HTTP 404)
    }

}

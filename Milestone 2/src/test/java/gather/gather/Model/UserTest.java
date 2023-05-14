package gather.gather.Model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {



    @Test
    public void testUserCreation() {
        // Create a user
        User user = new User("gail", "gail@example.com", "124");

        // Verify the user properties
        assertThat(user.getName()).isEqualTo("gail");
        assertThat(user.getEmail()).isEqualTo("gail@example.com");
        assertThat(user.getPassword()).isEqualTo("124");
    }

    @Test
    public void testSettersAndGetters() {
        // Create a user
        User user = new User("gail", "gail@example.com", "124");

        // Set new values using setters
        user.setName("aizl");
        user.setEmail("aizl@example.com");
        user.setPassword("000");

        // Verify the updated values using getters
        assertThat(user.getName()).isEqualTo("aizl");
        assertThat(user.getEmail()).isEqualTo("aizl@example.com");
        assertThat(user.getPassword()).isEqualTo("000");
    }


    @Test
    public void testSetIdAndGetId() {
        // Create a user
        User user = new User("gail", "gail@example.com", "124");

        // Set a new id
        user.setId("12345");

        // Verify the updated id using getId
        assertThat(user.getId()).isEqualTo("12345");
    }



    @Test
    public void testUserEqualityWithDifferentValues() {
        // Create two users with different values
        User user1 = new User("gail", "gail@example.com", "123");
        User user2 = new User("gail", "gail@example.com", "456");

        // Verify that the users are not considered equal
        assertThat(user1).isNotEqualTo(user2);
    }

}
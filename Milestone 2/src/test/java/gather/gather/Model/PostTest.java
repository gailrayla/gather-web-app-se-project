package gather.gather.Model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class PostTest {

    @Test
    public void testPostModel() {
        // Create a user
        User user = new User("gail", "gail@example.com", "124");

        // Create a date string
        String date = "30 October 2023";

        // Create a post
        Post post = new Post(user, "Post1", date);

        // Set the post ID
        post.setId("postId");

        // Set the creation date
        LocalDateTime createdAt = LocalDateTime.now();
        post.setCreatedAt(createdAt);

        // Verify the post data
        assertEquals("postId", post.getId());
        assertEquals(createdAt, post.getCreatedAt());
        assertEquals(user, post.getUser());
        assertEquals("Post1", post.getDesc());
        assertEquals(date, post.getDate());
    }

    @Test
    public void testSetAndGetId() {
        // Create a Post object
        Post post = new Post(new User("gail", "gail@example.com", "124"), "Post1", "30 October 2023");

        // Set a specific ID
        post.setId("postId");

        // Get the ID and verify it
        String postId = post.getId();
        Assertions.assertEquals("postId", postId);
    }

    @Test
    public void testSetAndGetCreatedAt() {
        // Create a Post object
        Post post = new Post(new User("gail", "gail@example.com", "124"), "Post1", "30 October 2023");

        // Set a specific creation date
        LocalDateTime createdAt = LocalDateTime.of(2023, 10, 15, 10, 30, 0);
        post.setCreatedAt(createdAt);

        // Get the creation date and verify it
        LocalDateTime retrievedCreatedAt = post.getCreatedAt();
        Assertions.assertEquals(createdAt, retrievedCreatedAt);
    }

    @Test
    public void testSetAndGetUser() {
        // Create a User object
        User user = new User("gail", "gail@example.com", "124");

        // Create a Post object and set the user
        Post post = new Post(null, "Post1", "30 October 2023");
        post.setUser(user);

        // Get the user and verify it
        User retrievedUser = post.getUser();
        Assertions.assertEquals(user, retrievedUser);
    }

    @Test
    public void testSetAndGetDesc() {
        // Create a Post object
        Post post = new Post(new User("gail", "gail@example.com", "124"), null, "30 October 2023");

        // Set a specific description
        post.setDesc("New description");

        // Get the description and verify it
        String desc = post.getDesc();
        Assertions.assertEquals("New description", desc);
    }

    @Test
    public void testSetAndGetDate() {
        // Create a Post object
        Post post = new Post(new User("gail", "gail@example.com", "124"), "Post1", null);

        // Set a specific date
        post.setDate("01 November 2023");

        // Get the date and verify it
        String date = post.getDate();
        Assertions.assertEquals("01 November 2023", date);
    }
    @Test
    public void testToString() {
        //User user = new User("John Doe", "johndoe@example.com", "password");
        Post post = new Post(new User("gail", "gail@example.com", "124"), "Post1", "2023-05-20");

        String expectedString = "Post{" +
                "id='null'" +
                ", CreatedAt=" + null +
                ", user=" + post.getUser() +
                ", desc='Post1'" +
                ", date='2023-05-20'" +
                '}';


        assertEquals(expectedString, post.toString());
    }
}
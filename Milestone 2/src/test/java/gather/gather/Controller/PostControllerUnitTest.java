package gather.gather.Controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoCollection;
import gather.gather.Controller.PostController;
import gather.gather.Model.Post;
import gather.gather.Model.User;
import gather.gather.Repository.PostRepository;
import gather.gather.Repository.SearchRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.swing.text.Document;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = PostController.class)
public class PostControllerUnitTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    private PostRepository postRepository;
    @MockBean
    private SearchRepository searchRepository;

    @Test // Test Case 1: Getting all posts in a non-empty database
    public void testGetAllPosts_NonEmpty() throws Exception {
        // Create mock posts
        List<Post> mockPosts = Arrays.asList(
                new Post(new User("gail", "gail@example.com", "124"), "Post1", "30 October 2023"),
                new Post(new User("reese", "reese@example.com", "567"), "Post2", "06 April 2023")
        );

        // Mock the repository's findAll() method to return the mock posts
        when(postRepository.findAll()).thenReturn(mockPosts);

        // Perform the GET request to "/posts"
        mockMvc.perform(get("/posts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // Ensure the request is successful
                .andExpect(jsonPath("$.length()").value(mockPosts.size())) // Check if the response JSON has the expected length
                .andExpect(jsonPath("$[0].user.name").value(mockPosts.get(0).getUser().getName())) // Assert that the value of the name property of the user object at index 0 in the response JSON matches the name of the user in the first mockPosts object
                .andExpect(jsonPath("$[0].user.email").value(mockPosts.get(0).getUser().getEmail())) // Assert that the value of the email property of the user object at index 0 in the response JSON matches the email of the user in the first mockPosts object
                .andExpect(jsonPath("$[0].desc").value(mockPosts.get(0).getDesc())) // Assert that the value of the desc property at index 0 in the response JSON matches the description of the first mockPosts object
                .andExpect(jsonPath("$[0].date").value(mockPosts.get(0).getDate())) // Assert that the value of the date property at index 0 in the response JSON matches the date of the first mockPosts object
                .andExpect(jsonPath("$[1].user.name").value(mockPosts.get(1).getUser().getName()))
                .andExpect(jsonPath("$[1].user.email").value(mockPosts.get(1).getUser().getEmail()))
                .andExpect(jsonPath("$[1].desc").value(mockPosts.get(1).getDesc()))
                .andExpect(jsonPath("$[1].date").value(mockPosts.get(1).getDate()));
    }

    @Test // Test Case 2: Getting all posts in an empty database
    public void testGetAllPosts_Empty() throws Exception {
        when(postRepository.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/posts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }


    @Test // Test Case 3: Creating a new post successfully
    public void testAddPost_Success() throws Exception {
        Post post = new Post(new User("gail", "gail@example.com", "124"), "Post1", "30 October 2023");

        when(postRepository.save(post)).thenReturn(post);

        mockMvc.perform(post("/posts") // performing the post request
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(post)))
                .andExpect(status().isOk()); // expecting a successful response
    }

    @Test // Test Case 4: Adding a post when there are missing fields --> Description
    public void testAddPost_MissingDescription() throws Exception {
        Post post = new Post(new User("gail", "gail@example.com", "124"), null, "30 October 2023");

        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(post)))
                .andExpect(status().isBadRequest());
    }

    @Test // Test Case 5: Adding a post when there are missing fields --> Date
    public void testAddPost_MissingDate() throws Exception {
        Post post = new Post(new User("gail", "gail@example.com", "124"), "Post1", null);

        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(post)))
                .andExpect(status().isBadRequest());
    }

    @Test // Test Case 6: Adding a post when there are missing fields --> User
    public void testAddPost_NullUser() throws Exception {
        Post post = new Post(null, "Post1", "30 October 2023");

        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(post)))
                .andExpect(status().isBadRequest());
    }

    @Test // Test Case 6: Adding a post when there are missing fields --> Date and Description
    public void testAddPost_MissingDescriptionAndDate() throws Exception {
        Post post = new Post(new User("gail", "gail@example.com", "124"), null, null);

        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(post)))
                .andExpect(status().isBadRequest());
    }

    @Test // Test Case 7: Adding a valid post with the join field is set to 0
    public void testAddPost_JoinNull() throws Exception {
        Post post = new Post(new User("gail", "gail@example.com", "124"), "Post1", "30 October 2023");

        when(postRepository.save(post)).thenReturn(post);

        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(post)))
                .andExpect(status().isOk());
    }

    @Test
    public void testPostDeletionAfterAWeek() throws InterruptedException {
        // Create a test post
        Post post = new Post(new User("gail", "gail@example.com", "124"), "Post1", "30 October 2023");

        // Set the createdAt time to a week in the future
        LocalDateTime futureTime = LocalDateTime.now().plusDays(7);
        post.setCreatedAt(futureTime);

        // Save the post to the database
        postRepository.save(post);

        // Sleep for a sufficient amount of time to allow for the post to be deleted
        Thread.sleep(5_000); // Wait for 5 seconds

        // Attempt to retrieve the post from the database
        Post foundPost = postRepository.findById(post.getId()).orElse(null);

        // Assert that the post has been deleted
        assertTrue(foundPost == null);
    }

    @Test
    public void deletePostTest() throws Exception {
        String id = "123";

        mockMvc.perform(MockMvcRequestBuilders.delete("/posts/delete/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(postRepository).deleteById(id);
    }
}



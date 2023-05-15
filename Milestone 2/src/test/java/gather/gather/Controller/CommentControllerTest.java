package gather.gather.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import gather.gather.Model.Comment;
import gather.gather.Model.User;
import gather.gather.Repository.CommentRepository;
import gather.gather.Repository.PostRepository;
import gather.gather.Repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CommentController.class)
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentRepository commentRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PostRepository postRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test // Test Case 1: Adding a comment successfully
    void testCreateComment_Success() throws Exception {
        User user = new User("rayla","rayla@example.com","aizl123");
        user.setId("user1");

        Comment comment = new Comment(user, "Test123");

        when(commentRepository.save(any(Comment.class))).thenReturn(comment);
        when(userRepository.existsById("user1")).thenReturn(true);
        when(postRepository.existsById("postId")).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.post("/posts/{postId}/comments", "postId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(comment))) // Use objectMapper.writeValueAsString()
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value("user1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").value("Test123"))
                .andDo(print());
    }

    @Test // Test Case 2: Getting comment through post id
    void testGetCommentsByPostId_Success() throws Exception {
        User user1 = new User("rayla","rayla@example.com","aizl123");
        user1.setId("user1");
        User user2 = new User("aizl", "aizl@example.com", "gail123");
        user2.setId("user2");

        Comment comment1 = new Comment(user1, "Comment 1");
        Comment comment2 = new Comment(user2,"Comment 2");
        List<Comment> comments = Arrays.asList(comment1, comment2);

        when(commentRepository.findByPostId("postId")).thenReturn(comments);

        mockMvc.perform(MockMvcRequestBuilders.get("/posts/{postId}/comments", "postId"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].userId").value("user1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].content").value("Comment 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].userId").value("user2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].content").value("Comment 2"));
    }

    @Test // Test Case 3: Adding comment when the comment is invalid
    void testAddComment_InvalidComment() throws Exception {
        User user = new User("rayla","rayla@example.com","aizl123");
        user.setId("user1");

        Comment comment = new Comment(user, "");

        when(userRepository.existsById("user1")).thenReturn(true);
        when(postRepository.existsById("postId")).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.post("/posts/{postId}/comments", "postId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(comment)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(print());
    }

    @Test // Test Case 4: Adding comment when user is not found
    void testAddComment_UserNotFound() throws Exception {
        User user = new User("rayla","rayla@example.com","aizl123");
        user.setId("user1");

        Comment comment = new Comment(user, "Test comment");

        when(userRepository.existsById("user1")).thenReturn(false);
        when(postRepository.existsById("postId")).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.post("/posts/{postId}/comments", "postId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(comment)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(print());
    }

    @Test // Test Case 5: Adding comment when post is not found
    void testAddComment_PostNotFound() throws Exception {
        User user = new User("rayla","rayla@example.com","aizl123");
        user.setId("user1");

        Comment comment = new Comment(user, "Test comment");

        when(userRepository.existsById("user1")).thenReturn(true);
        when(postRepository.existsById("postId")).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.post("/posts/{postId}/comments", "postId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(comment)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(print());
    }

    @Test // Test Case 6: Deleting comment successfully
    void testDeleteComment_Success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/comments/{commentId}", "commentId"))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(print());
    }

    @Test // Test Case 7: Deleting when comment is not found
    void testDeleteComment_CommentNotFound() throws Exception {
        doThrow(new EmptyResultDataAccessException(1)).when(commentRepository).deleteById("commentId");

        mockMvc.perform(MockMvcRequestBuilders.delete("/comments/{commentId}", "commentId"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(print());
    }
}
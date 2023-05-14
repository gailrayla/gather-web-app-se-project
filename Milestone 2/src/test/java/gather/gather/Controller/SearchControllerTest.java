package gather.gather.Controller;

import gather.gather.Model.Post;
import gather.gather.Model.User;
import gather.gather.Repository.SearchRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;

import static com.mongodb.internal.connection.tlschannel.util.Util.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WebMvcTest(SearchController.class)
public class SearchControllerTest {


    @MockBean
    private SearchRepository searchRepository;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByText() {
        // Setup
        String text = "one";
        Post post1 = new Post(new User("gail", "gail@example.com", "124"), "four five six", null);
        Post post2 = new Post(new User("badraa", "badraa@example.com", "1234"), "one two three", null);
        List<Post> expectedPosts = Arrays.asList(post2);
        when(searchRepository.findByText(text)).thenReturn(expectedPosts);

        // Execute
        List<Post> actualPosts = searchRepository.findByText(text);

        // Verify
        assertEquals(expectedPosts.size(), actualPosts.size());
        assertTrue(actualPosts.containsAll(expectedPosts));
        verify(searchRepository).findByText(text);
    }
}

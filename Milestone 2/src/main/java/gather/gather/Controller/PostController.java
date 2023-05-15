package gather.gather.Controller;

import gather.gather.Model.Post;
import gather.gather.Repository.PostRepository;
import gather.gather.Repository.SearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
public class PostController {

    @Autowired
    PostRepository repo;

    @GetMapping("/posts/access")
    @CrossOrigin
    public List<Post> GetAllPosts(){
        return repo.findAll();
    }

    @PostMapping("/posts/create")
    @CrossOrigin
    public ResponseEntity<Object> addPost(@RequestBody Post post) {
        if (!isValidPost(post)) {
            return ResponseEntity.badRequest().body("Invalid post data");
        }

        Post savedPost = repo.save(post);
        return ResponseEntity.ok(savedPost);
    }

    private boolean isValidPost(Post post) {
        // Perform validation checks on the post object
        // Return true if the post is valid, otherwise return false

        // Example validation: Check if the user is null or any other required fields are missing
        if (post.getUser() == null || post.getDesc() == null || post.getDate() == null) {
            return false;
        }

        return true;
    }

    @DeleteMapping("/posts/delete/{id}")
    public void deletePost(@PathVariable String id) {
        repo.deleteById(id);
    }
    // posts are being deleted after a week automatically
    // fixedRate attribute specifies the interval at which the method will be invoked
    @Scheduled(fixedRate = 86400000) // Run every day
    public void deleteExpiredPosts() {
        LocalDateTime currentTime = LocalDateTime.now();
        List<Post> expiredPosts = repo.findByCreatedAtBefore(currentTime.minusDays(7));
        repo.deleteAll(expiredPosts);
    }

}

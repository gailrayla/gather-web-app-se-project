package gather.gather.Controller;

import gather.gather.Model.Comment;
import gather.gather.Repository.CommentRepository;
import gather.gather.Repository.PostRepository;
import gather.gather.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/posts/{postId}/comments")
    public List<Comment> getCommentsByPostId(@PathVariable String postId) {
        return commentRepository.findByPostId(postId);
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<Comment> addComment(@PathVariable String postId, @RequestBody Comment comment) {
        if (!isValidComment(comment)) {
            return ResponseEntity.badRequest().build();
        }

        // Check if the user and post exist
        if (!userRepository.existsById(comment.getUserId()) || !postRepository.existsById(postId)) {
            return ResponseEntity.notFound().build();
        }

        comment.setPostId(postId);
        Comment savedComment = commentRepository.save(comment);
        return ResponseEntity.ok(savedComment);
    }

    @DeleteMapping("/comments/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable String commentId) {
        try {
            commentRepository.deleteById(commentId);
        } catch (EmptyResultDataAccessException e) {
            // Comment not found, return 404
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found", e);
        }
    }

    private void validateComment(Comment comment) {
        if (comment.getUserId() == null || comment.getUserId().isEmpty()) {
            throw new IllegalArgumentException("User ID must not be null or empty");
        }

        if (comment.getContent() == null || comment.getContent().isEmpty()) {
            throw new IllegalArgumentException("Comment content must not be null or empty");
        }
    }

    private boolean isValidComment(Comment comment) {
        try {
            validateComment(comment);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}

/* curl commands
new user: curl -X POST -H "Content-Type: application/json" -d '{
  "name": "aizltest",
  "email": "aizltest@example.com",
  "password": "aizl123"
}' http://localhost:8080/users/signup

new post: curl -X POST -H "Content-Type: application/json" -d '{
  "user": {
    "id": "USER_ID"   // Replace USER_ID with the actual user ID obtained from the previous response
  },
  "desc": "This is a test post for test comment",
  "date": "2023-05-15"
}' http://localhost:8080/posts

new comment: curl -X POST -H "Content-Type: application/json" -d '{
  "user": {
    "id": "USER_ID"   // Replace USER_ID with the actual user ID obtained from the previous response
  },
  "content": "This is a comment on the post"
}' http://localhost:8080/posts/POST_ID/comments


 */

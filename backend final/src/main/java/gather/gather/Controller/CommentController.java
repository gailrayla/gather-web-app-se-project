package gather.gather.Controller;

import gather.gather.Model.Comment;
import gather.gather.Model.Post;
import gather.gather.Repository.CommentRepository;
import gather.gather.Repository.PostRepository;
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

        // Check if the post exists
        if (!postRepository.existsById(postId)) {
            return ResponseEntity.notFound().build();
        }

        comment.setPostId(postId);
        Comment savedComment = commentRepository.save(comment);

        // Update the corresponding Post object with the new comment
        Post post = postRepository.findById(postId).orElse(null);
        if (post != null) {
            post.addComment(savedComment);
            postRepository.save(post);
        }

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
        if (comment.getUser() == null || comment.getUser().getId() == null || comment.getUser().getId().isEmpty()) {
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
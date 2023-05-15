package gather.gather.Model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Collection;

@Document(collection = "comments")
public class Comment {
    @Id
    private String id;

    @CreatedDate
    private LocalDateTime createdAt;
    private User user;
    private String content;
    private String postId;

    public Comment(User user, String content) {
        this.user = user;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id='" + id + '\'' +
                ", createdAt=" + createdAt +
                ", user=" + user +
                ", content='" + content + '\'' +
                ", postId='" + postId + '\'' +
                '}';
    }

    public String getUserId() {
        return user.getId();
    }

    public void setUserId(String userId) {
        user.setId(userId);
    }

    public String getText() {
        return content;
    }

    public void setText(String text) {
        this.content = text;
    }
}

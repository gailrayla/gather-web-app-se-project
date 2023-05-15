package gather.gather.Model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "posts")
public class Post {
    @Id
    private String id;

    @Indexed(expireAfterSeconds = 604800) // Set expiry for 7 days (24 * 60 * 60 * 7 seconds)
    @CreatedDate
    private LocalDateTime createdAt;
    private User user;
    private String desc;
    private String date;
    private List<Comment> comments = new ArrayList<>();

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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }

    public void removeComment(Comment comment) {
        this.comments.remove(comment);
    }

    public Post(User user, String desc, String date) {
        this.user = user;
        this.desc = desc;
        this.date = date;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id='" + id + '\'' +
                ", createdAt=" + createdAt +
                ", user=" + user +
                ", desc='" + desc + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}

package gather.gather.Model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.time.LocalDateTime;

@Document(collection = "posts")
public class Post {
    @Id
    private String id;

    @Indexed(expireAfterSeconds = 604800) // Set expiry for 7 days (24 * 60 * 60 * 7 seconds)
    @CreatedDate
    private LocalDateTime CreatedAt;
    private User user;
    private String desc;
    private String date;
    public String getId() {
        return id;
    }
    public LocalDateTime getCreatedAt() {
        return CreatedAt;
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

    public void setId(String id) {
        this.id = id;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        CreatedAt = createdAt;
    }

    public Post(User user, String desc, String date) {
        this.CreatedAt = CreatedAt;
        this.user = user;
        this.desc = desc;
        this.date = date;
    }

    //public Post(int date) {
    //this.date = date;
    //}


    @Override
    public String toString() {
        return "Post{" +
                "id='" + id + '\'' +
                ", CreatedAt=" + CreatedAt +
                ", user=" + user +
                ", desc='" + desc + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}

package gather.gather.Repository;

import gather.gather.Model.Comment;
import gather.gather.Model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends MongoRepository<Post,String> {
    List<Post> findByCreatedAtBefore(LocalDateTime localDateTime);
}

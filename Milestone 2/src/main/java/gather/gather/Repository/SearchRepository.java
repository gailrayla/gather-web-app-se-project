package gather.gather.Repository;

import gather.gather.Model.Post;

import java.util.List;

public interface SearchRepository {
    List<Post> findByText(String text);
}

package gather.gather.Controller;

import gather.gather.Model.Post;
import gather.gather.Repository.SearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
public class SearchController {
    @Autowired
    SearchRepository searchRepo;

    @GetMapping("/posts/{text}")
    @CrossOrigin
    public List<Post> search(@PathVariable("text")  String text)
    {
        return searchRepo.findByText(text);
    }
}

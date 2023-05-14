package gather.gather.Repository;

import gather.gather.Model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
// Here "User" is the entity type
// "UserRepository" provides several built-in methods from the MongoRepository,
//  such as save, findById, findAll, and delete.
public interface UserRepository extends MongoRepository<User, String> {
    // findByEmail method retrieves a user by their email
    User findByEmail(String email);
    //User findById();
    // existsByEmail method checks if a user with a given email exists in the database
    boolean existsByEmail(String email);
}

package gather.gather.Repository;

import gather.gather.Model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


@DataMongoTest
public class UserRepositoryTest {

    // naming tests is important PokemonRepository_SaveAll_ReturnSavePokemon()
    @Autowired
    private UserRepository userRepository;

    @Test
    public void UserRepository_findByEmail_ReturnUser() {
        String uniqueEmail = "gail@example.com" + UUID.randomUUID().toString();
        User user = new User("gail", uniqueEmail, "124");

        // Check if a user with the same email already exists
        User existingUser = userRepository.findByEmail(uniqueEmail);
        if (existingUser != null) {
            userRepository.delete(existingUser);
        }

        userRepository.save(user);

        User foundUser = userRepository.findByEmail(uniqueEmail);

        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getEmail()).isEqualTo(uniqueEmail);
    }


    @Test
    public void UserRepository_existsByEmail() {
        User user = new User("gail","gail@example.com","124");
        userRepository.save(user);

        boolean exists = userRepository.existsByEmail("gail@example.com");

        assertThat(exists).isTrue();
    }

    @Test
    public void UserRepository_existsByEmail_NotFound(){
        boolean exists = userRepository.existsByEmail("nonexistent@example.com");
        assertThat(exists).isFalse();
    }
}
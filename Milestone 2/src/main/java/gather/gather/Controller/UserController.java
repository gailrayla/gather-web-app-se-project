package gather.gather.Controller;

import gather.gather.Model.User;
import gather.gather.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
// "/users" is a prefix for the URL mapping
// It allows you to group related endpoints under a common base URL (../users/signup...)
// When the UserController class is annotated with @RequestMapping("/users"), it means that
// all the endpoints defined within this class will be relative to the /users path.
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    // handling HTTP POST requests
    // signUp method is invoked when a POST request is made to the /users/signup URL
    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody User user) {
        if (user.getName() == null || user.getName().isEmpty() ||
                user.getEmail() == null || user.getEmail().isEmpty() ||
                user.getPassword() == null || user.getPassword().isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid input");
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body("Email already exists");
        }

        User newUser = new User(user.getName(), user.getEmail(), user.getPassword());
        userRepository.save(newUser);
        return ResponseEntity.ok("User signed up successfully");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") String id, @RequestBody User userDTO) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (isValidUser(userDTO)) {
                user.setName(userDTO.getName());
                user.setEmail(userDTO.getEmail());
                user.setPassword(userDTO.getPassword());
                userRepository.save(user);
                return ResponseEntity.ok(user);
            } else {
                String errorMessage = "Invalid user information";
                return ResponseEntity.badRequest().body(errorMessage);
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private boolean isValidUser(User user) {
        return user != null && user.getEmail() != null && user.getName() != null && user.getPassword() != null;
    }


    @GetMapping("/access/{id}")
    public ResponseEntity<?> accessUser(@PathVariable String id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            userRepository.deleteById(id);
            return ResponseEntity.ok("User deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

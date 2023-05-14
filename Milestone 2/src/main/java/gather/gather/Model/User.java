package gather.gather.Model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

//@Data
// "@Document" is used to map the User class to a MongoDB collection called "users"
@Document(collection = "user")
public class User {
    // "@Id" is used to indicate the primary identifier field of the document (unique identifier)
    @Id
    private String id;
    private String name;
    private String email;
    private String password;

    // default constructor
    //public User() {
    //}

    // parameterized constructor
    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }



    // Getters and setters for id, name, email, and password
    // used to access and modify the private fields of a class

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}

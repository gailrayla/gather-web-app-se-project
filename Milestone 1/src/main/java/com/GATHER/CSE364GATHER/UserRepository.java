package com.GATHER.CSE364GATHER;

import org.apache.catalina.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {

    User findByUsername(String username);

    User findByEmail(String email);

    User findByStudentNumber(int studentId);

    List<User> findByFirstName(String firstName);

    List<User> findByLastName(String lastName);

    List<User> findByGender(String gender);

}

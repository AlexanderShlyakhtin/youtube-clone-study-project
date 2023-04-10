package dev.alex.example.studyyoutubeclone.backend.repository;

import dev.alex.example.studyyoutubeclone.backend.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findBySub(String sub);
}

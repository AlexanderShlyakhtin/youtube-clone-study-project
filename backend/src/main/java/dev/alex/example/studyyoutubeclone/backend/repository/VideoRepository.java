package dev.alex.example.studyyoutubeclone.backend.repository;

import dev.alex.example.studyyoutubeclone.backend.model.Video;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends MongoRepository<Video, String> {
}

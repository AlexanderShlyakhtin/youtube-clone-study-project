package dev.alex.example.studyyoutubeclone.backend.dto;

import dev.alex.example.studyyoutubeclone.backend.model.VideoStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoDto {

    private String id;
    private String title;
    private String description;
    private List<String> tags;
    private String videoUrl;
    private VideoStatus videoStatus;
    private AtomicInteger viewCount = new AtomicInteger(0);
    private String thumbnailUrl;
    private Integer likeCount;
    private Integer dislikeCount;

}

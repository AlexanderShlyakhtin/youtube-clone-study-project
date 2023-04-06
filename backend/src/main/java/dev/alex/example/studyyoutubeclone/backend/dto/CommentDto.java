package dev.alex.example.studyyoutubeclone.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private String commentText;
    private String commentAuthor;
    private int likeCount;
    private int disLikeCount;
}

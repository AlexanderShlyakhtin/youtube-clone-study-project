package dev.alex.example.studyyoutubeclone.backend.service;

import dev.alex.example.studyyoutubeclone.backend.model.Video;
import dev.alex.example.studyyoutubeclone.backend.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final S3FileService s3FileService;
    private final VideoRepository videoRepository;
    public void uploadVideo(MultipartFile multipartFile) {
        String videoUrl = s3FileService.uploadFile(multipartFile);
        var video = new Video();
        video.setVideoUrl(videoUrl);

        videoRepository.save(video);
    }
}

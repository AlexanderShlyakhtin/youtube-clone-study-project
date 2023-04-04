package dev.alex.example.studyyoutubeclone.backend.service;

import dev.alex.example.studyyoutubeclone.backend.dto.UploadVideoResponse;
import dev.alex.example.studyyoutubeclone.backend.dto.VideoDto;
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
    public UploadVideoResponse uploadVideo(MultipartFile multipartFile) {
        String videoUrl = s3FileService.uploadFile(multipartFile);
        var video = new Video();
        video.setVideoUrl(videoUrl);

        videoRepository.save(video);
        return new UploadVideoResponse(video.getId(), video.getVideoUrl());
    }

    public VideoDto editVideo(VideoDto videoDto) {
        Video video = getVideoById(videoDto.getId());
        video.setTitle(video.getTitle());
        video.setDescription(video.getDescription());
        video.setTags(video.getTags());
        video.setThumbnailUrl(videoDto.getThumbnailUrl());
        video.setVideoStatus(videoDto.getVideoStatus());

        videoRepository.save(video);
        return videoDto;
    }

    public String uploadThumbnail(MultipartFile file, String videoId) {
        Video videoById = getVideoById(videoId);

        String thumbnailUrl = s3FileService.uploadFile(file);

        videoById.setThumbnailUrl(thumbnailUrl);

        videoRepository.save(videoById);
        return thumbnailUrl;
    }

    private Video getVideoById(String videoId) {
        Video video = videoRepository.findById(videoId).orElseThrow(() ->
                new IllegalArgumentException("Cannot find video by id - " + videoId));
        return video;
    }

}

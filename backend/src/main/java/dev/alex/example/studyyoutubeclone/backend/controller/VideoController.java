package dev.alex.example.studyyoutubeclone.backend.controller;

import dev.alex.example.studyyoutubeclone.backend.dto.CommentDto;
import dev.alex.example.studyyoutubeclone.backend.dto.UploadVideoResponse;
import dev.alex.example.studyyoutubeclone.backend.dto.VideoDto;
import dev.alex.example.studyyoutubeclone.backend.model.Comment;
import dev.alex.example.studyyoutubeclone.backend.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/videos")
@RequiredArgsConstructor
public class VideoController {
    private final VideoService videoService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UploadVideoResponse uploadVideo(@RequestParam("file") MultipartFile file) {
        return videoService.uploadVideo(file);
    }

    @PostMapping("/thumbnail")
    @ResponseStatus(HttpStatus.CREATED)
    public String uploadThumbnail(@RequestParam("file") MultipartFile file, @RequestParam("videoId") String videoId) {
        return videoService.uploadThumbnail(file, videoId);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public VideoDto editVideoMetadata(@RequestBody VideoDto videoDto) {
       return videoService.editVideo(videoDto);

    }

    @GetMapping("/{videoId}")
    @ResponseStatus(HttpStatus.OK)
    public VideoDto getVideoDto(@PathVariable String videoId) {
        return videoService.getVideoDetails(videoId);
    }

    @PostMapping("/{videoId}/like")
    @ResponseStatus(HttpStatus.OK)
    public VideoDto likeVideo(@PathVariable String videoId) {
        return videoService.likeVideo(videoId);
    }

    @PostMapping("/{videoId}/disLike")
    @ResponseStatus(HttpStatus.OK)
    public VideoDto dislikeVideo(@PathVariable String videoId) {
        return videoService.dislikeVideo(videoId);
    }

    @PostMapping("/{videoId}/comment")
    @ResponseStatus(HttpStatus.OK)
    public void addComment(@PathVariable String videoId, @RequestBody CommentDto comment) {
        videoService.addComment(videoId, comment);
    }

    @GetMapping("/{videoId}/comment")
    @ResponseStatus(HttpStatus.OK)
    public List<CommentDto> getAllComments(@PathVariable String videoId) {
        return videoService.getAllComments(videoId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<VideoDto> getAllVideos() {
        return videoService.getAllVideos();
    }





}

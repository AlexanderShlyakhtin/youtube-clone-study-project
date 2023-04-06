package dev.alex.example.studyyoutubeclone.backend.service;

import dev.alex.example.studyyoutubeclone.backend.dto.CommentDto;
import dev.alex.example.studyyoutubeclone.backend.dto.UploadVideoResponse;
import dev.alex.example.studyyoutubeclone.backend.dto.VideoDto;
import dev.alex.example.studyyoutubeclone.backend.model.Comment;
import dev.alex.example.studyyoutubeclone.backend.model.Video;
import dev.alex.example.studyyoutubeclone.backend.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final S3FileService s3FileService;
    private final VideoRepository videoRepository;
    private final UserService userService;
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

    public void increaseVideoCount(Video savedVideo) {
        savedVideo.increaseViewCount();
        videoRepository.save(savedVideo);
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

    public VideoDto getVideoDetails(String videoId) {
        Video videoById = getVideoById(videoId);

        increaseVideoCount(videoById);
        userService.addVideoToHistory(videoId);

        return mapToVideoDto(videoById);
    }

    public VideoDto likeVideo(String videoId) {
        Video videoById = getVideoById(videoId);

        if(userService.ifLikedVideo(videoId)) {
            videoById.decreaseLikeCount();
            userService.removeFromLikeVideo(videoId);
        } else if (userService.ifDisLikedVideo(videoId)) {
            videoById.decreaseDisLikeCount();
            userService.removeFromDislikeVideos(videoId);
            videoById.increaseLikeCount();
            userService.addLikeToCurrentUser(videoId);
        } else {
            videoById.increaseLikeCount();
            userService.addLikeToCurrentUser(videoId);
        }

        videoRepository.save(videoById);

        return mapToVideoDto(videoById);
    }

    public VideoDto dislikeVideo(String videoId) {
        Video videoById = getVideoById(videoId);

        if(userService.ifDisLikedVideo(videoId)) {
            videoById.decreaseDisLikeCount();
            userService.removeFromDislikeVideos(videoId);
        } else if (userService.ifLikedVideo(videoId)) {
            videoById.decreaseLikeCount();
            userService.removeFromLikeVideo(videoId);
            videoById.increaseDisLikeCount();
            userService.addToDislikedVideos(videoId);
        } else {
            videoById.increaseDisLikeCount();
            userService.addToDislikedVideos(videoId);
        }

        videoRepository.save(videoById);

        return mapToVideoDto(videoById);
    }

    private static VideoDto mapToVideoDto(Video videoById) {
        VideoDto videoDto = new VideoDto();
        videoDto.setId(videoById.getId());
        videoDto.setVideoUrl(videoById.getVideoUrl());
        videoDto.setThumbnailUrl(videoById.getThumbnailUrl());
        videoDto.setDescription(videoById.getDescription());
        videoDto.setTitle(videoById.getTitle());
        videoDto.setTags(videoById.getTags());
        videoDto.setVideoStatus(videoById.getVideoStatus());
        videoDto.setDislikeCount(videoById.getDisLikes().get());
        videoDto.setLikeCount(videoById.getLikes().get());
        videoDto.setViewCount(videoById.getViewCount());
        return videoDto;
    }

    public void addComment(String videoId, CommentDto commentDto) {
        Video video = getVideoById(videoId);
        Comment comment = new Comment();
        comment.setText(commentDto.getCommentText());
        comment.setAuthor(commentDto.getCommentAuthor());

        video.addComment(comment);

        videoRepository.save(video);
    }

    public List<CommentDto> getAllComments(String videoId) {
        Video videoById = getVideoById(videoId);
        List<Comment> comments = videoById.getComments();

        List<CommentDto> commentDtos = comments.stream()
                .map(comment -> mapToCommentDto(comment))
                .toList();
        return commentDtos;
    }

    private CommentDto mapToCommentDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setCommentText(comment.getText());
        commentDto.setCommentAuthor(comment.getAuthor());

        return commentDto;
    }

    public List<VideoDto> getAllVideos() {
        return videoRepository.findAll().stream()
                .map(VideoService::mapToVideoDto)
                .toList();
    }
}

package dev.alex.example.studyyoutubeclone.backend.service;

import dev.alex.example.studyyoutubeclone.backend.model.User;
import dev.alex.example.studyyoutubeclone.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getCurrnetUser() {
        String sub = ((Jwt) (SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal())).getClaim("sub");
        return userRepository.findBySub(sub)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find user with sub - " + sub));
    }

    public void addLikeToCurrentUser(String videoId) {
        User currnetUser = getCurrnetUser();
        currnetUser.addToLikedVideos(videoId);
        userRepository.save(currnetUser);
    }

    public boolean ifLikedVideo(String videoId) {
        return getCurrnetUser().getLikedVideos().stream()
                .anyMatch(likedVideo -> likedVideo.equals(videoId));
    }

    public boolean ifDisLikedVideo(String videoId) {
        return getCurrnetUser().getDisLikedVideos().stream()
                .anyMatch(likedVideo -> likedVideo.equals(videoId));
    }

    public void removeFromLikeVideo(String videoId) {
        User currnetUser = getCurrnetUser();
        currnetUser.removeFromLikedVideos(videoId);
        userRepository.save(currnetUser);
    }

    public void removeFromDislikeVideos(String videoId) {
        User currnetUser = getCurrnetUser();
        currnetUser.removeFromDisLikedVideo(videoId);
        userRepository.save(currnetUser);
    }

    public void addToDislikedVideos(String videoId) {
        User currnetUser = getCurrnetUser();
        currnetUser.addToDisLikedVideo(videoId);
        userRepository.save(currnetUser);
    }

    public void addVideoToHistory(String videoById) {
        User currnetUser = getCurrnetUser();
        currnetUser.addToVideoHistory(videoById);
        userRepository.save(currnetUser);
    }

    public void subscribeUser(String userId) {
        User currnetUser = getCurrnetUser();
        currnetUser.addToSubscribedUsers(userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find user with userId - " + userId));
        user.addToSubscribers(currnetUser.getId());
        userRepository.save(currnetUser);
        userRepository.save(user);

    }

    public void unSubscribeUser(String userId) {
        User currnetUser = getCurrnetUser();
        currnetUser.removeFromSubscribedUsers(userId);

        User user = getUserById(userId);
        user.removeFromSubscribers(currnetUser.getId());
        userRepository.save(currnetUser);
        userRepository.save(user);

    }

    public Set<String> getUserHistory(String userId) {
        User user = getUserById(userId);
        return user.getVideoHistory();
    }

    private User getUserById(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find user with userId - " + userId));
    }
}

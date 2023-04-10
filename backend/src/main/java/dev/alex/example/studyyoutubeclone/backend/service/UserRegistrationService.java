package dev.alex.example.studyyoutubeclone.backend.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.alex.example.studyyoutubeclone.backend.dto.UserInfoDto;
import dev.alex.example.studyyoutubeclone.backend.model.User;
import dev.alex.example.studyyoutubeclone.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserRegistrationService {

    @Value("${auth0.userInfoEndpoint}")
    private String userInfoEndpoint;

    private final UserRepository userRepository;

    public String registration(String tokenValue) {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(userInfoEndpoint))
                .setHeader("Authorization", String.format("Bearer %s", tokenValue))
                .build();
        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();

        try {
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            String body = response.body();

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            UserInfoDto userInfoDto = objectMapper.readValue(body, UserInfoDto.class);

            Optional<User> userBySubject = userRepository.findBySub(userInfoDto.getSub());
            if (userBySubject.isPresent()) {
                return userBySubject.get().getId();
            } else {
                User user = new User();
                user.setFirstName(userInfoDto.getGivenName());
                user.setLastName(userInfoDto.getFamilyName());
                user.setFullName(userInfoDto.getName());
                user.setSub(userInfoDto.getSub());
                user.setEmailAddress(userInfoDto.getEmail());

                return userRepository.save(user).getId();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}

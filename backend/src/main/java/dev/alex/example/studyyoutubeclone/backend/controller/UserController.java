package dev.alex.example.studyyoutubeclone.backend.controller;

import dev.alex.example.studyyoutubeclone.backend.service.UserRegistrationService;
import dev.alex.example.studyyoutubeclone.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserRegistrationService userRegistrationService;
    private final UserService userService;

    @GetMapping("/register")
    public String register(Authentication authentication) {
        Jwt jwt = (Jwt) authentication.getPrincipal();
        userRegistrationService.registration(jwt.getTokenValue());
        return "User Registration successful";
    }

    @PostMapping("/subscribe/${userId}")
    public boolean subscribeUser(@PathVariable String userId) {
        userService.subscribeUser(userId);
        return true;
    }

    @PostMapping("/unSubscribe/${userId}")
    public boolean unSubscribeUser(@PathVariable String userId) {
        userService.unSubscribeUser(userId);
        return true;
    }
}

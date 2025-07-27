package ru.svanchukov.Token_5_Task.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.svanchukov.Token_5_Task.dto.AuthRequest;
import ru.svanchukov.Token_5_Task.dto.AuthResponse;
import ru.svanchukov.Token_5_Task.dto.UserDto;
import ru.svanchukov.Token_5_Task.service.AuthService;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDto userDto) {
        authService.register(userDto);
        return ResponseEntity.ok("User registered successfully");
    }

    public ResponseEntity<AuthResponse> refresh(@RequestBody Map<String, String> payload) {
        String refreshToken = payload.get("refreshToken");
        return ResponseEntity.ok(authService.refresh(refreshToken));
    }
}

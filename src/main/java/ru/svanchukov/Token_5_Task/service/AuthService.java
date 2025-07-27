package ru.svanchukov.Token_5_Task.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.svanchukov.Token_5_Task.dto.AuthRequest;
import ru.svanchukov.Token_5_Task.dto.AuthResponse;
import ru.svanchukov.Token_5_Task.dto.UserDto;
import ru.svanchukov.Token_5_Task.entity.User;
import ru.svanchukov.Token_5_Task.repository.UserRepository;
import ru.svanchukov.Token_5_Task.service.JWT.JwtService;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthResponse login(AuthRequest auth) {
        User user = userRepository.findByUsername(auth.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!passwordEncoder.matches(auth.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        String accessToken = jwtService.generateToken(user.getUsername(), 5 * 60 * 1000);
        String refreshToken = jwtService.generateToken(user.getUsername(), 30 * 60 * 1000);

        user.setToken(refreshToken);
        userRepository.save(user);

        return new AuthResponse(accessToken, refreshToken);
    }

    public AuthResponse refresh(String refreshToken) {
        String username;

        try {
            username = jwtService.validateTokenAndGetUsername(refreshToken);
        } catch (Exception e) {
            throw new BadCredentialsException("Invalid refresh token");
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!refreshToken.equals(user.getToken())) {
            throw new BadCredentialsException("Refresh token mismatch");
        }

        String newAccessToken = jwtService.generateToken(username, 5 * 60 * 1000);
        return new AuthResponse(newAccessToken, refreshToken);
    }

    public AuthResponse register(UserDto userDto) {
        if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("User already exists");
        }

        String encodedPassword = passwordEncoder.encode(userDto.getPassword());

        // Генерация токенов
        String accessToken = jwtService.generateToken(userDto.getUsername(), 5 * 60 * 1000);
        String refreshToken = jwtService.generateToken(userDto.getUsername(), 30 * 60 * 1000);

        User user = new User(null, userDto.getUsername(), encodedPassword, refreshToken);
        userRepository.save(user);

        // Возврат токенов
        return new AuthResponse(accessToken, refreshToken);
    }

}









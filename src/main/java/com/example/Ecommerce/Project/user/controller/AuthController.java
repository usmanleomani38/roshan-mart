package com.example.Ecommerce.Project.user.controller;

import com.example.Ecommerce.Project.auth.authservice.AuthService;
import com.example.Ecommerce.Project.auth.jwtresponsedto.JwtResponse;
import com.example.Ecommerce.Project.exeptionhandler.ApiResponse;
import com.example.Ecommerce.Project.user.dto.request.LoginRequest;
import com.example.Ecommerce.Project.user.dto.request.SignUpRequest;
import com.example.Ecommerce.Project.user.dto.request.response.ProfileDTO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest request, HttpServletResponse response) {
        return authService.validate(request, response);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody SignUpRequest request) {
        return authService.registerUser(request);

    }

    @GetMapping("/get-profile")
    public ResponseEntity<ApiResponse<ProfileDTO>> getMyProfile() {
        return authService.getMyProfile();

    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> refreshAccessToken(@CookieValue(name = "refreshToken", required = false) String refreshToken) {
        if (refreshToken == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return authService.validateRefreshToken(refreshToken);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse response) {

        Cookie cookie = new Cookie("refreshToken", null);
        cookie.setHttpOnly(true);
        cookie.setPath("/api/auth/refresh");
        cookie.setMaxAge(0); // delete immediately
        response.addCookie(cookie);
        return ResponseEntity.ok("Logged out successfully");
    }


}

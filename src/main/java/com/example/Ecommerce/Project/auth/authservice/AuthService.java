package com.example.Ecommerce.Project.auth.authservice;

import com.example.Ecommerce.Project.auth.config.JwtConfig;
import com.example.Ecommerce.Project.auth.jwtresponsedto.JwtResponse;
import com.example.Ecommerce.Project.auth.jwtservice.JwtService;
import com.example.Ecommerce.Project.auth.userdetailservice.CustomUserDetails;
import com.example.Ecommerce.Project.exeptionhandler.ApiResponse;
import com.example.Ecommerce.Project.exeptionhandler.Status;
import com.example.Ecommerce.Project.role.model.AppRole;
import com.example.Ecommerce.Project.role.model.Role;
import com.example.Ecommerce.Project.role.repo.RoleRepo;
import com.example.Ecommerce.Project.user.dto.request.LoginRequest;
import com.example.Ecommerce.Project.user.dto.request.SignUpRequest;
import com.example.Ecommerce.Project.user.dto.request.response.ProfileDTO;
import com.example.Ecommerce.Project.user.model.User;
import com.example.Ecommerce.Project.user.repo.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static com.example.Ecommerce.Project.appcontants.commonutils.Methods.getCurrentUserEmail;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final JwtConfig jwtConfig;
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;
    private  User user;


    public ResponseEntity<JwtResponse> validate(LoginRequest request,
                                                HttpServletResponse response) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // Get User details after successful authentication
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        user = userDetails.getUser();
        Set<Role> roles = user.getRoles();




        // 1. Generate Tokens
        var accessToken = jwtService.generateAccessToken(request.getEmail(), roles);
        var refreshToken = jwtService.generateRefreshToken(request.getEmail(), roles);

        // 2. Set Refresh Token as HTTP-Only Cookie
        var cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/api/auth/refresh"); // Use the actual refresh endpoint path

        // 3. Set MaxAge
        // MaxAge must be set in seconds (int)
        cookie.setMaxAge((jwtConfig.getRefreshTokenExpiration() / 1000));

        // cookie.setSecure(true); // Uncomment in production over HTTPS
        response.addCookie(cookie);

        // 4. Return Access Token in the response body
        return ResponseEntity.ok(new JwtResponse(accessToken));
    }



    // Called from the AuthController @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> validateRefreshToken(String refreshToken) {
        if (!jwtService.validateToken(refreshToken))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        else {
            // 1. Extract user details from the valid Refresh Token
            var email = jwtService.getEmailFromToken(refreshToken);
            Set<String> roles = jwtService.getRolesFromToken(refreshToken); // Must extract role for new token
            Set<Role> rolesInSet = new HashSet<>();
            for(String role : roles) {
                Role tempRole = new Role();
                tempRole.setRoleName(AppRole.valueOf(role));
                rolesInSet.add(tempRole);
            }
            // 2. Generate a new Access Token (FIXED SIGNATURE)
            var accessToken = jwtService.generateAccessToken(email,rolesInSet);

            return ResponseEntity.ok(new JwtResponse(accessToken));
        }
    }

    @Transactional
    public ResponseEntity<ApiResponse<?>> registerUser(SignUpRequest request) {

        if (request.getEmail() == null || request.getEmail().trim().isEmpty()
                || request.getPassword() == null || request.getPassword().trim().isEmpty()) {
            ApiResponse<?> response = ApiResponse.builder()
                    .status(Status.ERROR)
                    .message("Email and Password are Required!")
                    .build();
            return ResponseEntity.badRequest().body(response);
        }
        if (userRepo.existsByUserName(request.getUserName())) {
            ApiResponse<?> response = ApiResponse.builder()
                    .status(Status.ERROR)
                    .message("User name is already taken!.")
                    .build();
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
        if (userRepo.existsByEmail(request.getEmail())) {
            ApiResponse<?> response = ApiResponse.builder()
                    .status(Status.ERROR)
                    .message("Email address is already registered.")
                    .build();
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }

        User newUser = new User();
        newUser.setEmail(request.getEmail());
        String hashedPassword = passwordEncoder.encode(request.getPassword());
        newUser.setPassword(hashedPassword);
        newUser.setUserName(request.getUserName());


        Set<String> strRoles = request.getRole();
        Set<Role> roles = new HashSet<>();
        if (strRoles == null) {
            Role userRole = roleRepo.findByRoleName(AppRole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepo.findByRoleName(AppRole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "seller":
                        Role modRole = roleRepo.findByRoleName(AppRole.ROLE_SELLER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepo.findByRoleName(AppRole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        newUser.setRoles(roles);
        newUser.setCreatedAt(LocalDateTime.now());
        userRepo.save(newUser);

        ApiResponse<?> response = ApiResponse.builder()
                .status(Status.SUCCESS)
                .message("User registered successfully! Role: " + newUser.getRoles())
                .build();
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<ProfileDTO>> getMyProfile() {
        String email = getCurrentUserEmail();

        System.out.println("ProfileService DEBUG: Attempting to fetch profile for email: " + email);

        User userProfile = userRepo.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Authenticated user credentials not found for email: " + email));

        ApiResponse<ProfileDTO> response = ApiResponse.<ProfileDTO>builder()
                .status(Status.SUCCESS)
                .message("Profile fetched successfully")
                .data(ProfileDTO.toDTO(userProfile))
                .build();
        return ResponseEntity.ok(response);
    }

}
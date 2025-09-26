package com.kirusanth.schoolhouse.auth;

import com.kirusanth.schoolhouse.user.User;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final AuthService auth;

  public AuthController(AuthService auth) {
    this.auth = auth;
  }

  @PostMapping("/register")
  public AuthResponse register(@RequestBody @Valid RegisterRequest req) {
    return auth.register(req.getName(), req.getEmail(), req.getPassword(), req.getRole());
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody @Valid LoginRequest req) {
    try {
      return ResponseEntity.ok(auth.login(req.getEmail(), req.getPassword()));
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Invalid email or password"));
    }
  }

  @GetMapping("/me")
  public ResponseEntity<?> me(Authentication authentication) {
    if (authentication == null || authentication.getName() == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Unauthorized"));
    }
    User u = auth.getByEmail(authentication.getName());
    return ResponseEntity.ok(Map.of(
        "id", u.getId(),
        "name", u.getName(),
        "email", u.getEmail(),
        "role", u.getRole()
    ));
  }
}
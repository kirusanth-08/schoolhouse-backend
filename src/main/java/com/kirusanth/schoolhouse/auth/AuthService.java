package com.kirusanth.schoolhouse.auth;

import com.kirusanth.schoolhouse.entity.User;
import com.kirusanth.schoolhouse.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthService {
  private final UserRepository users;
  private final PasswordEncoder encoder;
  private final JwtService jwt;

  public AuthService(UserRepository users, PasswordEncoder encoder, JwtService jwt) {
    this.users = users;
    this.encoder = encoder;
    this.jwt = jwt;
  }

  public AuthResponse register(String name, String email, String password, String role) {
    if (users.existsByEmail(email)) {
      throw new IllegalStateException("Email already registered");
    }
    User u = new User();
    u.setName(name);
    u.setEmail(email.toLowerCase());
    u.setRole(role.toLowerCase());
    u.setPasswordHash(encoder.encode(password));
    u = users.save(u);
    String token = jwt.generateToken(u.getEmail(), Map.of("uid", u.getId(), "name", u.getName(), "role", u.getRole()));
    return new AuthResponse(token, u.getId(), u.getName(), u.getEmail(), u.getRole());
  }

  public AuthResponse login(String email, String password) {
    User u = users.findByEmail(email.toLowerCase()).orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));
    if (!encoder.matches(password, u.getPasswordHash())) {
      throw new IllegalArgumentException("Invalid credentials");
    }
    String token = jwt.generateToken(u.getEmail(), Map.of("uid", u.getId(), "name", u.getName(), "role", u.getRole()));
    return new AuthResponse(token, u.getId(), u.getName(), u.getEmail(), u.getRole());
  }

  public User getByEmail(String email) {
    return users.findByEmail(email.toLowerCase()).orElseThrow(() -> new IllegalArgumentException("User not found"));
  }
}
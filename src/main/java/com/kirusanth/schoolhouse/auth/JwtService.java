package com.kirusanth.schoolhouse.auth;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

  private final Key key;
  private final long expirationMs;

  public JwtService(
      @Value("${app.jwt.secret}") String secret,
      @Value("${app.jwt.expiration-ms:2592000000}") long expirationMs) {

    byte[] keyBytes;
    if (secret.startsWith("base64:")) {
      // Allow explicitly base64-encoded secrets via prefix
      String b64 = secret.substring("base64:".length());
      keyBytes = Decoders.BASE64.decode(b64);
    } else {
      // Treat as raw text secret
      keyBytes = secret.getBytes(StandardCharsets.UTF_8);
    }

    if (keyBytes.length < 32) {
      throw new IllegalStateException("JWT secret must be at least 32 bytes. Increase app.jwt.secret length.");
    }

    this.key = Keys.hmacShaKeyFor(keyBytes);
    this.expirationMs = expirationMs;
  }

  public String generateToken(String subject, Map<String, Object> claims) {
    long now = System.currentTimeMillis();
    return Jwts.builder()
        .setSubject(subject)
        .addClaims(claims)
        .setIssuedAt(new Date(now))
        .setExpiration(new Date(now + expirationMs))
        .signWith(key, SignatureAlgorithm.HS256)
        .compact();
  }

  public Jws<Claims> parse(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token);
  }
}
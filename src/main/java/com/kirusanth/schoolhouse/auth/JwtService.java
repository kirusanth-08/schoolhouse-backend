package com.kirusanth.schoolhouse.auth;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.DecodingException;
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

    byte[] keyBytes = resolveKeyBytes(secret);

    if (keyBytes.length < 32) {
      throw new IllegalStateException("JWT secret must be at least 32 bytes. Increase app.jwt.secret length.");
    }

    this.key = Keys.hmacShaKeyFor(keyBytes);
    this.expirationMs = expirationMs;
  }

  private static byte[] resolveKeyBytes(String secret) {
    String s = secret;
    boolean prefixed = false;
    if (s != null && s.startsWith("base64:")) {
      s = s.substring("base64:".length());
      prefixed = true;
    }

    if (prefixed) {
      try {
        return Decoders.BASE64.decode(s);
      } catch (DecodingException e1) {
        try {
          return Decoders.BASE64URL.decode(s);
        } catch (DecodingException e2) {
          // fall through to raw bytes
        }
      }
    }
    return s.getBytes(StandardCharsets.UTF_8);
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
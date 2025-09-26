package com.kirusanth.schoolhouse.user;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

  private final UserRepository users;

  public ProfileController(UserRepository users) {
    this.users = users;
  }

  @GetMapping("me")
  public ProfileDto me(Authentication auth) {
    var u = users.findByEmail(auth.getName()).orElseThrow(() -> new IllegalArgumentException("User not found"));
    return ProfileDto.from(u);
  }

  public record UpdateProfileRequest(String name) {}

  @PutMapping("me")
  @ResponseStatus(HttpStatus.OK)
  public ProfileDto update(Authentication auth, @RequestBody UpdateProfileRequest req) {
    var u = users.findByEmail(auth.getName()).orElseThrow(() -> new IllegalArgumentException("User not found"));
    if (req.name() != null && !req.name().isBlank()) u.setName(req.name());
    return ProfileDto.from(users.save(u));
  }

  public static class ProfileDto {
    public Long id;
    public String name;
    public String email;
    public String role;

    static ProfileDto from(User u) {
      var d = new ProfileDto();
      d.id = u.getId();
      d.name = u.getName();
      d.email = u.getEmail();
      d.role = u.getRole();
      return d;
    }
  }
}
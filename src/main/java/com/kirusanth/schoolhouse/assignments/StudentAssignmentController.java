package com.kirusanth.schoolhouse.assignments;

import com.kirusanth.schoolhouse.user.User;
import com.kirusanth.schoolhouse.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/student/assignments")
public class StudentAssignmentController {

  private final StudentAssignmentRepository repo;
  private final AssignmentRepository assignments;
  private final UserRepository users;

  public StudentAssignmentController(StudentAssignmentRepository repo, AssignmentRepository assignments, UserRepository users) {
    this.repo = repo;
    this.assignments = assignments;
    this.users = users;
  }

  // List my assignments with minimal fields the UI uses
  @GetMapping
  public List<StudentAssignmentDto> myAssignments(Authentication auth) {
    var email = auth.getName();
    return repo.findByUserEmail(email).stream().map(StudentAssignmentDto::from).toList();
  }

  // Quick stats for cards (pending/submitted/graded)
  @GetMapping("/stats")
  public Stats stats(Authentication auth) {
    var email = auth.getName();
    var list = repo.findByUserEmail(email);
    long pending = list.stream().filter(sa -> sa.getStatus() == StudentAssignmentStatus.pending).count();
    long submitted = list.stream().filter(sa -> sa.getStatus() == StudentAssignmentStatus.submitted).count();
    long graded = list.stream().filter(sa -> sa.getStatus() == StudentAssignmentStatus.graded).count();
    return new Stats(pending, submitted, graded);
  }

  public record SubmitRequest(String comments) {}

  // Submit an assignment (JSON body with optional comments)
  @PostMapping("{assignmentId}/submit")
  @ResponseStatus(HttpStatus.OK)
  public StudentAssignmentDto submit(Authentication auth, @PathVariable Long assignmentId, @RequestBody(required = false) SubmitRequest body) {
    var email = auth.getName();
    var sa = repo.findByUserEmailAndAssignmentId(email, assignmentId).orElseGet(() -> {
      User user = users.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("User not found"));
      Assignment a = assignments.findById(assignmentId).orElseThrow(() -> new IllegalArgumentException("Assignment not found"));
      var ns = new StudentAssignment();
      ns.setUser(user);
      ns.setAssignment(a);
      return ns;
    });

    sa.setStatus(StudentAssignmentStatus.submitted);
    sa.setSubmittedAt(Instant.now());
    if (body != null && body.comments() != null) sa.setComments(body.comments());
    return StudentAssignmentDto.from(repo.save(sa));
  }

  // DTOs
  public record Stats(long pending, long submitted, long graded) {}

  public static class StudentAssignmentDto {
    public Long id;
    public Long assignmentId;
    public String title;
    public String subject;
    public String dueDate; // ISO date
    public String status;  // pending|submitted|graded
    public String grade;   // optional
    public String description;

    static StudentAssignmentDto from(StudentAssignment sa) {
      var a = sa.getAssignment();
      var dto = new StudentAssignmentDto();
      dto.id = sa.getId();
      dto.assignmentId = a.getId();
      dto.title = a.getTitle();
      dto.subject = a.getSubject();
      dto.dueDate = a.getDueDate().toString();
      dto.status = sa.getStatus().name();
      dto.grade = sa.getGrade();
      dto.description = a.getDescription();
      return dto;
    }
  }
}
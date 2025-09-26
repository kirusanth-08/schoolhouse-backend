package com.kirusanth.schoolhouse.assignments;

import com.kirusanth.schoolhouse.user.User;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "student_assignments", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "assignment_id"}))
public class StudentAssignment {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional = false) @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne(optional = false) @JoinColumn(name = "assignment_id")
  private Assignment assignment;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private StudentAssignmentStatus status = StudentAssignmentStatus.pending;

  private String grade; // e.g., A, B+, etc.
  private Instant submittedAt;
  @Column(length = 2000) private String comments;

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public User getUser() { return user; }
  public void setUser(User user) { this.user = user; }
  public Assignment getAssignment() { return assignment; }
  public void setAssignment(Assignment assignment) { this.assignment = assignment; }
  public StudentAssignmentStatus getStatus() { return status; }
  public void setStatus(StudentAssignmentStatus status) { this.status = status; }
  public String getGrade() { return grade; }
  public void setGrade(String grade) { this.grade = grade; }
  public Instant getSubmittedAt() { return submittedAt; }
  public void setSubmittedAt(Instant submittedAt) { this.submittedAt = submittedAt; }
  public String getComments() { return comments; }
  public void setComments(String comments) { this.comments = comments; }
}
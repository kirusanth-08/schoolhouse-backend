package com.kirusanth.schoolhouse.assignments;

import jakarta.persistence.*;
import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "assignments")
public class Assignment {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false) private String title;
  @Column(nullable = false) private String subject;

  @Column(columnDefinition = "TEXT")
  private String description;

  @Column(nullable = false) private Integer points = 100;
  @Column(nullable = false) private Integer totalStudents = 0;
  @Column(nullable = false) private Integer submissions = 0;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private AssignmentStatus status = AssignmentStatus.active;

  @Column(nullable = false) private LocalDate dueDate;
  @Column(nullable = false, updatable = false) private Instant createdAt = Instant.now();

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public String getTitle() { return title; }
  public void setTitle(String title) { this.title = title; }
  public String getSubject() { return subject; }
  public void setSubject(String subject) { this.subject = subject; }
  public String getDescription() { return description; }
  public void setDescription(String description) { this.description = description; }
  public Integer getPoints() { return points; }
  public void setPoints(Integer points) { this.points = points; }
  public Integer getTotalStudents() { return totalStudents; }
  public void setTotalStudents(Integer totalStudents) { this.totalStudents = totalStudents; }
  public Integer getSubmissions() { return submissions; }
  public void setSubmissions(Integer submissions) { this.submissions = submissions; }
  public AssignmentStatus getStatus() { return status; }
  public void setStatus(AssignmentStatus status) { this.status = status; }
  public LocalDate getDueDate() { return dueDate; }
  public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
  public Instant getCreatedAt() { return createdAt; }
  public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
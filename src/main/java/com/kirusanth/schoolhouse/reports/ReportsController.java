package com.kirusanth.schoolhouse.reports;

import com.kirusanth.schoolhouse.assignments.StudentAssignmentRepository;
import com.kirusanth.schoolhouse.assignments.StudentAssignmentStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportsController {

  private final StudentAssignmentRepository studentAssignments;

  public ReportsController(StudentAssignmentRepository studentAssignments) {
    this.studentAssignments = studentAssignments;
  }

  // Grades derived from graded student assignments
  @GetMapping("grades/my")
  public List<GradeReport> myGrades(Authentication auth) {
    var email = auth.getName();
    return studentAssignments.findByUserEmail(email).stream()
        .filter(sa -> sa.getStatus() == StudentAssignmentStatus.graded)
        .map(sa -> new GradeReport(
            sa.getAssignment().getSubject(),
            sa.getGrade() != null ? sa.getGrade() : "N/A",
            sa.getAssignment().getPoints() != null ? sa.getAssignment().getPoints() : 100
        ))
        .toList();
  }

  // Summary numbers for assignments: pending/submitted/graded
  @GetMapping("assignments/my")
  public AssignmentSummary myAssignmentSummary(Authentication auth) {
    var email = auth.getName();
    var list = studentAssignments.findByUserEmail(email);
    long pending = list.stream().filter(sa -> sa.getStatus() == StudentAssignmentStatus.pending).count();
    long submitted = list.stream().filter(sa -> sa.getStatus() == StudentAssignmentStatus.submitted).count();
    long graded = list.stream().filter(sa -> sa.getStatus() == StudentAssignmentStatus.graded).count();
    return new AssignmentSummary(pending, submitted, graded);
  }

  public record GradeReport(String subject, String grade, int points) {}
  public record AssignmentSummary(long pending, long submitted, long graded) {}
}
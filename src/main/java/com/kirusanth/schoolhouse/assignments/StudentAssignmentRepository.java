package com.kirusanth.schoolhouse.assignments;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentAssignmentRepository extends JpaRepository<StudentAssignment, Long> {
  List<StudentAssignment> findByUserEmail(String email);
  Optional<StudentAssignment> findByUserEmailAndAssignmentId(String email, Long assignmentId);
}
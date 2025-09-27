package com.kirusanth.schoolhouse.repository;

import com.kirusanth.schoolhouse.entity.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {}
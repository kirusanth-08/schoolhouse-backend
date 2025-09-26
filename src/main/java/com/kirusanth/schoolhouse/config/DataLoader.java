package com.kirusanth.schoolhouse.config;

import com.kirusanth.schoolhouse.library.Book;
import com.kirusanth.schoolhouse.library.BookRepository;
import com.kirusanth.schoolhouse.library.BookStatus;
import com.kirusanth.schoolhouse.user.User;
import com.kirusanth.schoolhouse.user.UserRepository;
import com.kirusanth.schoolhouse.assignments.Assignment;
import com.kirusanth.schoolhouse.assignments.AssignmentRepository;
import com.kirusanth.schoolhouse.assignments.AssignmentStatus;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class DataLoader {

  @Bean
  CommandLineRunner seedBooks(BookRepository repo) {
    return args -> {
      if (repo.count() > 0) return;

      Book b1 = new Book();
      b1.setTitle("Introduction to Calculus");
      b1.setAuthor("James Stewart");
      b1.setIsbn("978-0538497817");
      b1.setCategory("Mathematics");
      b1.setPublishedYear(2008);
      b1.setAvailable(true);
      b1.setStatus(BookStatus.available);

      Book b2 = new Book();
      b2.setTitle("Physics for Scientists and Engineers");
      b2.setAuthor("Raymond Serway");
      b2.setIsbn("978-1133947271");
      b2.setCategory("Physics");
      b2.setPublishedYear(2013);
      b2.setAvailable(false);
      b2.setStatus(BookStatus.borrowed);
      b2.setBorrowerName("Alice Johnson");
      b2.setBorrowerEmail("alice@example.com");
      b2.setBorrowedDate(LocalDate.now().minusDays(10));
      b2.setDueDate(LocalDate.now().plusDays(4));

      Book b3 = new Book();
      b3.setTitle("Chemistry: The Central Science");
      b3.setAuthor("Theodore L. Brown");
      b3.setIsbn("978-0321910417");
      b3.setCategory("Chemistry");
      b3.setPublishedYear(2014);
      b3.setAvailable(false);
      b3.setStatus(BookStatus.borrowed);
      b3.setBorrowerName("Bob Smith");
      b3.setBorrowerEmail("bob@example.com");
      b3.setBorrowedDate(LocalDate.now().minusDays(20));
      b3.setDueDate(LocalDate.now().minusDays(3));

      repo.saveAll(List.of(b1, b2, b3));
    };
  }

  @Bean
  CommandLineRunner seedUsers(UserRepository users, PasswordEncoder encoder) {
    return args -> {
      if (!users.existsByEmail("admin@example.com")) {
        User admin = new User();
        admin.setName("Admin");
        admin.setEmail("admin@example.com");
        admin.setRole("admin");
        admin.setPasswordHash(encoder.encode("admin123"));
        users.save(admin);
      }
    };
  }

  @Bean
  CommandLineRunner seedAssignments(AssignmentRepository repo) {
    return args -> {
      if (repo.count() > 0) return;
      Assignment a1 = new Assignment();
      a1.setTitle("Algebra Problem Set 3");
      a1.setSubject("Mathematics");
      a1.setDescription("Complete problems 1-20 on page 45");
      a1.setPoints(100);
      a1.setTotalStudents(25);
      a1.setSubmissions(18);
      a1.setStatus(AssignmentStatus.active);
      a1.setDueDate(java.time.LocalDate.now().plusDays(7));

      Assignment a2 = new Assignment();
      a2.setTitle("History Essay");
      a2.setSubject("History");
      a2.setDescription("Write a 5-page essay on the Industrial Revolution");
      a2.setPoints(150);
      a2.setTotalStudents(25);
      a2.setSubmissions(22);
      a2.setStatus(AssignmentStatus.overdue);
      a2.setDueDate(java.time.LocalDate.now().minusDays(2));

      repo.saveAll(java.util.List.of(a1, a2));
    };
  }
}
package com.kirusanth.schoolhouse.config;

import com.kirusanth.schoolhouse.library.Book;
import com.kirusanth.schoolhouse.library.BookRepository;
import com.kirusanth.schoolhouse.library.BookStatus;
import com.kirusanth.schoolhouse.user.User;
import com.kirusanth.schoolhouse.user.UserRepository;
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
}
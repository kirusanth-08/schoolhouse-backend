package com.kirusanth.schoolhouse.library;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
  List<Book> findByDueDateBeforeAndStatusNot(LocalDate date, BookStatus status);
}
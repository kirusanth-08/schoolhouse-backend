package com.kirusanth.schoolhouse.repository;

import com.kirusanth.schoolhouse.entity.Book;
import com.kirusanth.schoolhouse.library.BookStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
  List<Book> findByDueDateBeforeAndStatusNot(LocalDate date, BookStatus status);
}
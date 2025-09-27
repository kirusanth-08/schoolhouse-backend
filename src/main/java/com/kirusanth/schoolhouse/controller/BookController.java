package com.kirusanth.schoolhouse.controller;

import com.kirusanth.schoolhouse.entity.Book;
import com.kirusanth.schoolhouse.library.BorrowRequest;
import com.kirusanth.schoolhouse.library.ReminderRequest;
import com.kirusanth.schoolhouse.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/books")
public class BookController {

  private final BookService service;

  public BookController(BookService service) {
    this.service = service;
  }

  @GetMapping
  public List<Book> list() {
    return service.listAll();
  }

  @PostMapping
  public Book create(@RequestBody @Valid Book book) {
    return service.create(book);
  }

  @PutMapping("/{id}")
  public Book update(@PathVariable Long id, @RequestBody @Valid Book book) {
    return service.update(id, book);
  }

  @PostMapping("/{id}/borrow")
  public Book borrow(@PathVariable Long id, @RequestBody @Valid BorrowRequest req) {
    return service.borrow(id, req);
  }

  @PostMapping("/{id}/return")
  public Book returnBook(@PathVariable Long id) {
    return service.returnBook(id);
  }

  @GetMapping("/overdue")
  public List<Book> overdue() {
    return service.listOverdue();
  }

  @PostMapping("/remind")
  public ResponseEntity<?> remind(@RequestBody @Valid ReminderRequest req) {
    int count = service.sendReminders(req.getBookIds());
    return ResponseEntity.ok(Map.of("remindersSent", count));
  }
}
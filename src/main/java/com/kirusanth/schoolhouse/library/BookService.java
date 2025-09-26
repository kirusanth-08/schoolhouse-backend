package com.kirusanth.schoolhouse.library;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class BookService {

  private final BookRepository repo;

  @Value("${app.library.fine-per-day:0.50}")
  private BigDecimal finePerDay;

  @Value("${app.library.default-loan-days:14}")
  private int defaultLoanDays;

  public BookService(BookRepository repo) {
    this.repo = repo;
  }

  public List<Book> listAll() {
    List<Book> books = repo.findAll();
    books.forEach(this::computeDerivedFields);
    return books;
  }

  public Book get(Long id) {
    Book b = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Book not found: " + id));
    computeDerivedFields(b);
    return b;
  }

  @Transactional
  public Book create(Book book) {
    book.setId(null);
    if (book.isAvailable()) {
      book.setStatus(BookStatus.available);
    } else if (book.getStatus() == null) {
      book.setStatus(BookStatus.borrowed);
    }
    return repo.save(book);
  }

  @Transactional
  public Book update(Long id, Book payload) {
    Book b = get(id);
    b.setTitle(payload.getTitle());
    b.setAuthor(payload.getAuthor());
    b.setIsbn(payload.getIsbn());
    b.setCategory(payload.getCategory());
    b.setPublishedYear(payload.getPublishedYear());
    b.setAvailable(payload.isAvailable());
    if (payload.isAvailable()) {
      b.setStatus(BookStatus.available);
      b.setBorrowerName(null);
      b.setBorrowerEmail(null);
      b.setBorrowedDate(null);
      b.setDueDate(null);
      b.setReturnDate(null);
    } else {
      if (b.getStatus() == BookStatus.available) {
        b.setStatus(BookStatus.borrowed);
      }
    }
    return repo.save(b);
  }

  @Transactional
  public Book borrow(Long id, BorrowRequest req) {
    Book b = get(id);
    if (!b.isAvailable()) throw new IllegalStateException("Book is not available");
    b.setAvailable(false);
    b.setStatus(BookStatus.borrowed);
    b.setBorrowerName(req.getBorrowerName());
    b.setBorrowerEmail(req.getBorrowerEmail());
    LocalDate now = LocalDate.now();
    b.setBorrowedDate(now);
    int days = (req.getLoanPeriodDays() != null && req.getLoanPeriodDays() > 0) ? req.getLoanPeriodDays() : defaultLoanDays;
    b.setDueDate(now.plusDays(days));
    b.setReturnDate(null);
    return repo.save(b);
  }

  @Transactional
  public Book returnBook(Long id) {
    Book b = get(id);
    b.setAvailable(true);
    b.setStatus(BookStatus.available);
    b.setReturnDate(LocalDate.now());
    b.setBorrowerName(null);
    b.setBorrowerEmail(null);
    b.setBorrowedDate(null);
    b.setDueDate(null);
    return repo.save(b);
  }

  public List<Book> listOverdue() {
    List<Book> list = repo.findByDueDateBeforeAndStatusNot(LocalDate.now(), BookStatus.available);
    list.forEach(book -> {
      if (!book.isAvailable()) book.setStatus(BookStatus.overdue);
      computeDerivedFields(book);
    });
    return list;
  }

  public int sendReminders(List<Long> bookIds) {
    return (int) bookIds.stream().filter(repo::existsById).count();
  }

  private void computeDerivedFields(Book b) {
    if (b.getDueDate() != null && !b.isAvailable()) {
      long days = ChronoUnit.DAYS.between(b.getDueDate(), LocalDate.now());
      if (days > 0) {
        b.setDaysOverdue((int) days);
        b.setFine(finePerDay.multiply(BigDecimal.valueOf(days)));
        b.setStatus(BookStatus.overdue);
      } else {
        b.setDaysOverdue(0);
        b.setFine(BigDecimal.ZERO);
      }
    } else {
      b.setDaysOverdue(0);
      b.setFine(BigDecimal.ZERO);
    }
  }
}
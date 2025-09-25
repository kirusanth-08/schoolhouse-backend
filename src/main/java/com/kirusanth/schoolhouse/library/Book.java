package com.kirusanth.schoolhouse.library;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "books")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Book {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;
  private String author;
  private String isbn;
  private String category;
  private Integer publishedYear;

  private boolean available = true;

  @Enumerated(EnumType.STRING)
  private BookStatus status = BookStatus.available;

  private String borrowerName;
  private String borrowerEmail;

  private LocalDate borrowedDate;
  private LocalDate dueDate;
  private LocalDate returnDate;

  @Transient
  private Integer daysOverdue;

  @Transient
  private BigDecimal fine;

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public String getTitle() { return title; }
  public void setTitle(String title) { this.title = title; }
  public String getAuthor() { return author; }
  public void setAuthor(String author) { this.author = author; }
  public String getIsbn() { return isbn; }
  public void setIsbn(String isbn) { this.isbn = isbn; }
  public String getCategory() { return category; }
  public void setCategory(String category) { this.category = category; }
  public Integer getPublishedYear() { return publishedYear; }
  public void setPublishedYear(Integer publishedYear) { this.publishedYear = publishedYear; }
  public boolean isAvailable() { return available; }
  public void setAvailable(boolean available) { this.available = available; }
  public BookStatus getStatus() { return status; }
  public void setStatus(BookStatus status) { this.status = status; }
  public String getBorrowerName() { return borrowerName; }
  public void setBorrowerName(String borrowerName) { this.borrowerName = borrowerName; }
  public String getBorrowerEmail() { return borrowerEmail; }
  public void setBorrowerEmail(String borrowerEmail) { this.borrowerEmail = borrowerEmail; }
  public LocalDate getBorrowedDate() { return borrowedDate; }
  public void setBorrowedDate(LocalDate borrowedDate) { this.borrowedDate = borrowedDate; }
  public LocalDate getDueDate() { return dueDate; }
  public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
  public LocalDate getReturnDate() { return returnDate; }
  public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }
  public Integer getDaysOverdue() { return daysOverdue; }
  public void setDaysOverdue(Integer daysOverdue) { this.daysOverdue = daysOverdue; }
  public BigDecimal getFine() { return fine; }
  public void setFine(BigDecimal fine) { this.fine = fine; }
}
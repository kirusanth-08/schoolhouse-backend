package com.kirusanth.schoolhouse.library;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class BorrowRequest {
  @NotBlank
  private String borrowerName;

  @NotBlank
  @Email
  private String borrowerEmail;

  @Positive
  private Integer loanPeriodDays;

  public String getBorrowerName() { return borrowerName; }
  public void setBorrowerName(String borrowerName) { this.borrowerName = borrowerName; }
  public String getBorrowerEmail() { return borrowerEmail; }
  public void setBorrowerEmail(String borrowerEmail) { this.borrowerEmail = borrowerEmail; }
  public Integer getLoanPeriodDays() { return loanPeriodDays; }
  public void setLoanPeriodDays(Integer loanPeriodDays) { this.loanPeriodDays = loanPeriodDays; }
}
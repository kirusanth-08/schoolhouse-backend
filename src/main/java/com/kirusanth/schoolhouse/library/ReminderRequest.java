package com.kirusanth.schoolhouse.library;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public class ReminderRequest {
  @NotEmpty
  private List<Long> bookIds;

  public List<Long> getBookIds() { return bookIds; }
  public void setBookIds(List<Long> bookIds) { this.bookIds = bookIds; }
}
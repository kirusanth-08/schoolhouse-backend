package com.kirusanth.schoolhouse.schedule;

import com.kirusanth.schoolhouse.user.User;
import jakarta.persistence.*;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@Table(name = "schedule_entries")
public class ScheduleEntry {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional = false) @JoinColumn(name = "user_id")
  private User user;

  @Column(nullable = false) private String course;
  @Column(nullable = false) private String subject;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private DayOfWeek dayOfWeek;

  @Column(nullable = false) private LocalTime startTime;
  @Column(nullable = false) private LocalTime endTime;
  private String room;

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public User getUser() { return user; }
  public void setUser(User user) { this.user = user; }
  public String getCourse() { return course; }
  public void setCourse(String course) { this.course = course; }
  public String getSubject() { return subject; }
  public void setSubject(String subject) { this.subject = subject; }
  public DayOfWeek getDayOfWeek() { return dayOfWeek; }
  public void setDayOfWeek(DayOfWeek dayOfWeek) { this.dayOfWeek = dayOfWeek; }
  public LocalTime getStartTime() { return startTime; }
  public void setStartTime(LocalTime startTime) { this.startTime = startTime; }
  public LocalTime getEndTime() { return endTime; }
  public void setEndTime(LocalTime endTime) { this.endTime = endTime; }
  public String getRoom() { return room; }
  public void setRoom(String room) { this.room = room; }
}
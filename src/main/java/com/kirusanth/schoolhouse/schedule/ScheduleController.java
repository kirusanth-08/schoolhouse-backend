package com.kirusanth.schoolhouse.schedule;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {

  private final ScheduleEntryRepository repo;

  public ScheduleController(ScheduleEntryRepository repo) {
    this.repo = repo;
  }

  // Return current user's schedule
  @GetMapping("my")
  public List<ScheduleDto> my(Authentication auth) {
    var email = auth.getName();
    return repo.findByUserEmail(email).stream().map(ScheduleDto::from).toList();
  }

  public static class ScheduleDto {
    public Long id;
    public String course;
    public String subject;
    public String dayOfWeek;
    public String startTime;
    public String endTime;
    public String room;

    static ScheduleDto from(ScheduleEntry e) {
      var d = new ScheduleDto();
      d.id = e.getId();
      d.course = e.getCourse();
      d.subject = e.getSubject();
      d.dayOfWeek = e.getDayOfWeek().name();
      d.startTime = e.getStartTime().toString();
      d.endTime = e.getEndTime().toString();
      d.room = e.getRoom();
      return d;
    }
  }
}
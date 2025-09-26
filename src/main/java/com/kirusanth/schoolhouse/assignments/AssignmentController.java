package com.kirusanth.schoolhouse.assignments;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {
  private final AssignmentRepository repo;

  public AssignmentController(AssignmentRepository repo) {
    this.repo = repo;
  }

  @GetMapping
  public List<Assignment> list() {
    return repo.findAll();
  }

  @GetMapping("{id}")
  public Assignment get(@PathVariable Long id) {
    return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Assignment not found"));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Assignment create(@RequestBody Assignment a) {
    a.setId(null);
    return repo.save(a);
  }

  @PutMapping("{id}")
  public Assignment update(@PathVariable Long id, @RequestBody Assignment a) {
    Assignment existing = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Assignment not found"));
    existing.setTitle(a.getTitle());
    existing.setSubject(a.getSubject());
    existing.setDescription(a.getDescription());
    existing.setPoints(a.getPoints());
    existing.setTotalStudents(a.getTotalStudents());
    existing.setSubmissions(a.getSubmissions());
    existing.setStatus(a.getStatus());
    existing.setDueDate(a.getDueDate());
    return repo.save(existing);
  }

  @DeleteMapping("{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) {
    if (!repo.existsById(id)) throw new IllegalArgumentException("Assignment not found");
    repo.deleteById(id);
  }
}
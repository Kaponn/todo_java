package efs.task.todoapp.repository;

import java.util.Date;
import java.util.UUID;

public class TaskEntity {
  private UUID id;
  private String description;
  private Date due;

  public TaskEntity(UUID id, String description, Date due) {
    this.id = UUID.randomUUID();
    this.description = description;
    this.due = due;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Date getDue() {
    return due;
  }

  public void setDue(Date due) {
    this.due = due;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }
}

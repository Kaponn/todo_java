package efs.task.todoapp.repository;

import com.google.gson.Gson;
import efs.task.todoapp.constants.HttpStatus;
import efs.task.todoapp.util.HttpException;

import java.util.UUID;

public class TaskEntity {

  private UUID id;
  private String username;
  private String description;
  private String due;

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getDue() {
    return due;
  }

  public void setDue(String due) {
    this.due = due;
  }

  public String toJson() {
    return new Gson().toJson(this);
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void validateUser(String username) throws HttpException {
    if (!username.equals(this.username)) {
      throw new HttpException(HttpStatus.FORBIDDEN, "The task is not property of this user");
    }
  }
}

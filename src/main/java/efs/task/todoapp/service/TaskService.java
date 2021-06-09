package efs.task.todoapp.service;

import efs.task.todoapp.constants.HttpStatus;
import efs.task.todoapp.repository.TaskRepository;
import efs.task.todoapp.repository.TaskEntity;
import efs.task.todoapp.util.HttpException;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TaskService {
  private final TaskRepository taskRepository;

  public TaskService(TaskRepository taskRepository) {
    this.taskRepository = taskRepository;
  }

  public UUID addTask(Map<String, String> taskData, String username) {
      TaskEntity task = new TaskEntity();

      String description = taskData.get("description");
      String due = taskData.get("due");

      if (description == null || description.isBlank() || due == null || due.isBlank()) {
        throw new HttpException(HttpStatus.BAD_REQUEST, "Description or due is missing");
      }

      task.setUsername(username);
      task.setDescription(description);
      task.setDue(due);

    return taskRepository.save(task);
  }

  public TaskEntity getTask(UUID id) {
    return taskRepository.query(id);
  }

  public TaskEntity updateTask(UUID id, TaskEntity task) {
    return taskRepository.update(id, task);
  }

  public List<TaskEntity> getTasksOfUser(String username) {
    return taskRepository.query(task -> task.getUsername().equals(username));
  }

  public void deleteTask(UUID id) {
    taskRepository.delete(id);
  }
}

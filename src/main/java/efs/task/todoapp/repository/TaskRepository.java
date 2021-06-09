package efs.task.todoapp.repository;

import java.util.*;
import java.util.function.Predicate;

public class TaskRepository implements Repository<UUID, TaskEntity> {
    private final Map<UUID, TaskEntity> taskMap = new TreeMap<>();

    @Override
    public UUID save(TaskEntity taskEntity) {
        if (taskEntity == null) {
            return null;
        }
        UUID id = UUID.randomUUID();
        taskEntity.setId(id);
        taskMap.put(id, taskEntity);
        return id;
    }

    @Override
    public TaskEntity query(UUID uuid) {
        return taskMap.get(uuid);
    }

    @Override
    public List<TaskEntity> query(Predicate<TaskEntity> condition) {
        return new ArrayList<>(taskMap.values());
    }

    @Override
    public TaskEntity update(UUID uuid, TaskEntity taskEntity) {
        TaskEntity taskToUpdate = taskMap.get(uuid);
        String description = taskEntity.getDescription();
        if (description != null && !description.isBlank()) {
            taskToUpdate.setDescription(description);
        }

        String due = taskEntity.getDue();
        if (due != null && !due.isBlank()) {
            taskToUpdate.setDue(due);
        }

        return taskToUpdate;
    }

    @Override
    public boolean delete(UUID uuid) {
        return (taskMap.remove(uuid) != null);
    }
}

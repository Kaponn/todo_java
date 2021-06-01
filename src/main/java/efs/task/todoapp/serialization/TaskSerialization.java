package efs.task.todoapp.serialization;

import com.google.gson.*;
import efs.task.todoapp.repository.TaskEntity;

import java.lang.reflect.Type;

public class TaskSerialization implements JsonSerializer<TaskEntity> {
  @Override
  public JsonElement serialize(TaskEntity task, Type type, JsonSerializationContext jsonSerializationContext) {
    JsonObject taskJsonObject = new JsonObject();

    taskJsonObject.addProperty("id", task.getId().toString());

    taskJsonObject.addProperty("description", task.getDescription());

    taskJsonObject.addProperty("due", task.getDue().toString());

    return taskJsonObject;
  }

  Gson gson = new GsonBuilder()
      .setPrettyPrinting()
      .excludeFieldsWithoutExposeAnnotation()
      .serializeNulls()
      .disableHtmlEscaping()
      .registerTypeAdapter(TaskEntity.class, new TaskSerialization())
      .create();
}

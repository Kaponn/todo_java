package efs.task.todoapp.serialization;

import com.google.gson.*;
import efs.task.todoapp.repository.TaskEntity;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;

public class TaskSerialization implements JsonSerializer<TaskEntity> {
  private final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

  @Override
  public JsonElement serialize(TaskEntity task, Type type, JsonSerializationContext jsonSerializationContext) {
    JsonObject taskJsonObject = new JsonObject();

    taskJsonObject.addProperty("id", task.getId().toString());

    taskJsonObject.addProperty("description", task.getDescription());

    taskJsonObject.addProperty("due", task.getDue() != null ? sdf.format(task.getDue()) : null);

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

package efs.task.todoapp.deserialization;

import com.google.gson.*;
import efs.task.todoapp.repository.TaskEntity;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.UUID;

public class TaskDeserialization implements JsonDeserializer<TaskEntity> {
  private final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

  @Override
  public TaskEntity deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
    JsonObject jsonObject = json.getAsJsonObject();

    JsonElement jsonId = jsonObject.get("id");
    JsonElement jsonDescription = jsonObject.get("description");
    JsonElement jsonDue = jsonObject.get("due");

    String jsonIdAsString = jsonId.getAsString();

    try {
      return new TaskEntity(UUID.fromString(jsonIdAsString), jsonDescription.getAsString(), sdf.parse(jsonDue.getAsString()));
    } catch (ParseException e) {
      e.printStackTrace();
      return null;
    }
  }
}

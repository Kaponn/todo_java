package efs.task.todoapp.serialization;

import com.google.gson.*;
import efs.task.todoapp.repository.UserEntity;

import java.lang.reflect.Type;

public class UserSerialization implements JsonSerializer<UserEntity> {
  @Override
  public JsonElement serialize(UserEntity user, Type type, JsonSerializationContext jsonSerializationContext) {
    JsonObject userJsonObject = new JsonObject();

    userJsonObject.addProperty("id", user.getId().toString());

    userJsonObject.addProperty("username", user.getUsername());

    userJsonObject.addProperty("password", user.getPassword());

    return userJsonObject;
  }

  Gson gson = new GsonBuilder()
      .setPrettyPrinting()
      .excludeFieldsWithoutExposeAnnotation()
      .serializeNulls()
      .disableHtmlEscaping()
      .registerTypeAdapter(UserEntity.class, new UserSerialization())
      .create();
}

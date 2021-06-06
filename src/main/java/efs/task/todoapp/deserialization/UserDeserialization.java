package efs.task.todoapp.deserialization;

import com.google.gson.*;
import efs.task.todoapp.repository.UserEntity;

import java.lang.reflect.Type;
import java.util.UUID;

public class UserDeserialization implements JsonDeserializer<UserEntity> {
  @Override
  public UserEntity deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
    JsonObject jsonObject = json.getAsJsonObject();

    JsonElement jsonId = jsonObject.get("id");
    JsonElement jsonUsername = jsonObject.get("username");
    JsonElement jsonPassword = jsonObject.get("password");

    String jsonIdAsString = jsonId.getAsString();

    return new UserEntity(UUID.fromString(jsonIdAsString), jsonUsername.getAsString(), jsonPassword.getAsString());
  }
}

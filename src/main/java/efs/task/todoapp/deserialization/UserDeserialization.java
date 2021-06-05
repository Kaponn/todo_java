package efs.task.todoapp.deserialization;

import com.google.gson.*;
import efs.task.todoapp.repository.UserEntity;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.UUID;

public class UserDeserialization implements JsonDeserializer<UserEntity> {
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public UserEntity deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        JsonElement jsonId = jsonObject.get("id");
        JsonElement jsonUsername = jsonObject.get("username");
        JsonElement jsonPassword = jsonObject.get("password");

        String jsonIdAsString = jsonId.getAsString();

        UserEntity userGson = new UserEntity(UUID.fromString(jsonIdAsString), jsonUsername.getAsString(), jsonPassword.getAsString());
        return userGson;
    }
}

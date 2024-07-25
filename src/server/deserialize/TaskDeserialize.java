package server.deserialize;

import com.google.gson.*;
import model.Task;

import java.lang.reflect.Type;
import java.time.Duration;
import java.time.LocalDateTime;

public class TaskDeserialize implements JsonDeserializer<Task> {

    @Override
    public Task deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        return new Task(jsonObject.get("name").getAsString(), jsonObject.get("description").getAsString(),
                jsonDeserializationContext.deserialize(jsonObject.get("duration"), Duration.class),
                jsonDeserializationContext.deserialize(jsonObject.get("startTime"), LocalDateTime.class));
    }
}

package server.deserialize;

import com.google.gson.*;
import model.SubTask;

import java.lang.reflect.Type;
import java.time.Duration;
import java.time.LocalDateTime;

public class SubTaskDeserialize implements JsonDeserializer<SubTask> {
    @Override
    public SubTask deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        return new SubTask(jsonObject.get("name").getAsString(), jsonObject.get("description").getAsString(),
                jsonDeserializationContext.deserialize(jsonObject.get("duration"), Duration.class),
                jsonDeserializationContext.deserialize(jsonObject.get("startTime"), LocalDateTime.class),
                jsonObject.get("epicId").getAsInt());
    }
}

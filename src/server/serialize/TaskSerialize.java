package server.serialize;

import com.google.gson.*;
import model.Task;
import java.lang.reflect.Type;


public class TaskSerialize implements JsonSerializer<Task> {

    @Override
    public JsonElement serialize(Task task, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("name", task.getName());
        jsonObject.addProperty("description", task.getDescription());
        jsonObject.addProperty("id", task.getId());
        jsonObject.add("startTime", jsonSerializationContext.serialize(task.getStartTime()));
        jsonObject.add("duration", jsonSerializationContext.serialize(task.getStartTime()));
        jsonObject.add("endTime", jsonSerializationContext.serialize(task.getEndTime()));

        return jsonObject;
    }
}

package server.serialize;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import model.SubTask;

import java.lang.reflect.Type;

public class SubTaskSerialize implements JsonSerializer<SubTask> {
    @Override
    public JsonElement serialize(SubTask subTask, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("name", subTask.getName());
        jsonObject.addProperty("description", subTask.getDescription());
        jsonObject.addProperty("id", subTask.getId());
        jsonObject.add("startTime", jsonSerializationContext.serialize(subTask.getStartTime()));
        jsonObject.add("duration", jsonSerializationContext.serialize(subTask.getDuration()));
        jsonObject.add("endTime", jsonSerializationContext.serialize(subTask.getEndTime()));
        jsonObject.addProperty("epicId", subTask.getEpicId());

        return jsonObject;
    }
}

package server.serialize;

import com.google.gson.*;
import model.Epic;
import model.SubTask;

import java.lang.reflect.Type;

public class EpicSerialize implements JsonSerializer<Epic> {

    @Override
    public JsonElement serialize(Epic epic, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("name", epic.getName());
        jsonObject.addProperty("description", epic.getDescription());
        jsonObject.addProperty("id", epic.getId());
        jsonObject.add("startTime", jsonSerializationContext.serialize(epic.getStartTime()));
        jsonObject.add("duration", jsonSerializationContext.serialize(epic.getDuration()));
        jsonObject.add("endTime", jsonSerializationContext.serialize(epic.getEndTime()));

        JsonArray subTaskArray = new JsonArray();
        for (SubTask subTask : epic.getSubtasks()) {
            JsonObject subTaskObj = new JsonObject();
            subTaskObj.addProperty("name", subTask.getName());
            subTaskObj.addProperty("description", subTask.getDescription());
            subTaskObj.addProperty("id", subTask.getId());
            subTaskObj.add("startTime", jsonSerializationContext.serialize(subTask.getStartTime()));
            subTaskObj.add("duration", jsonSerializationContext.serialize(subTask.getStartTime()));
            subTaskObj.add("endTime", jsonSerializationContext.serialize(subTask.getEndTime()));

            subTaskArray.add(subTaskObj);
        }

        jsonObject.add("subtasks", subTaskArray);


        return jsonObject;
    }
}

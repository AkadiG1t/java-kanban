package server.deserialize;

import com.google.gson.*;
import model.Epic;

import java.lang.reflect.Type;

public class EpicDeserialize implements JsonDeserializer<Epic> {

    @Override
    public Epic deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        return new Epic(jsonObject.get("name").getAsString(), jsonObject.get("description").getAsString());
    }
}


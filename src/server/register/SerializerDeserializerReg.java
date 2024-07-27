package server.register;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Epic;
import model.SubTask;
import model.Task;
import server.adaptor.DurationAdapter;
import server.adaptor.LocalDateAdapter;
import server.deserialize.EpicDeserialize;
import server.deserialize.SubTaskDeserialize;
import server.deserialize.TaskDeserialize;
import server.serialize.EpicSerialize;
import server.serialize.SubTaskSerialize;
import server.serialize.TaskSerialize;

import java.time.Duration;
import java.time.LocalDateTime;

public class SerializerDeserializerReg {

    public static void registerSerializer(GsonBuilder gsonBuilder) {
        gsonBuilder.registerTypeAdapter(Duration.class, new DurationAdapter());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter());
        gsonBuilder.registerTypeAdapter(Task.class, new TaskSerialize());
        gsonBuilder.registerTypeAdapter(Epic.class, new EpicSerialize());
        gsonBuilder.registerTypeAdapter(SubTask.class, new SubTaskSerialize());
    }

    public static void registerDeserializer(GsonBuilder gsonBuilder) {
        gsonBuilder.registerTypeAdapter(Task.class, new TaskDeserialize());
        gsonBuilder.registerTypeAdapter(Epic.class, new EpicDeserialize());
        gsonBuilder.registerTypeAdapter(SubTask.class, new SubTaskDeserialize());
    }

    public static Gson createCustomGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        registerSerializer(gsonBuilder);
        registerDeserializer(gsonBuilder);
        return gsonBuilder.setPrettyPrinting().create();
    }
}



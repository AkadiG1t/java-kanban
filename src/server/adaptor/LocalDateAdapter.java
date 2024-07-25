package server.adaptor;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateAdapter extends TypeAdapter<LocalDateTime> {
        private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm dd.MMMM.yyyy");

        @Override
        public void write(final JsonWriter jsonWriter, LocalDateTime localDate) throws IOException {
            if (localDate == null) {
                jsonWriter.nullValue();
                return;
            }
            jsonWriter.value(localDate.format(DATE_TIME_FORMATTER));
        }

        @Override
        public LocalDateTime read(final JsonReader jsonReader) throws IOException {
            String dateString = jsonReader.nextString();
            if (dateString.isBlank()) {
                return null;
            }
            return LocalDateTime.parse(dateString, DATE_TIME_FORMATTER);
        }
}

package servicetest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import service.FileBackedTaskManager;
import service.InMemoryHistoryManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

 class FileBackedTaskManageTest {

    File file = Files.createTempFile("test", ".csv").toFile();
    FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(file, new InMemoryHistoryManager());


     FileBackedTaskManageTest() throws IOException {
     }

     @Test
    void saveAndLoadFromFile() {
        fileBackedTaskManager.save();
        Assertions.assertNotNull(FileBackedTaskManager.loadFromFile(file));
    }

}

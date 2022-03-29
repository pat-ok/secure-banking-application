package persistence;

import model.UserDatabase;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            UserDatabase udb = new UserDatabase(false);
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            UserDatabase udb = new UserDatabase(false);
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyUserDatabase.json");
            writer.open();
            writer.write(udb);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyUserDatabase.json");
            udb = reader.read();
            assertEquals(1, udb.getUserDatabase().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            UserDatabase udb = new UserDatabase(true);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralUserDatabase.json");
            writer.open();
            writer.write(udb);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralUserDatabase.json");
            udb = reader.read();
            checkAccount(udb, "foo", "pass123", "Mr. Foo", "100.00", 3, 0);
            checkAccount(udb, "bar", "pass123", "Mr. Bar", "100.00", 3, 0);

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}

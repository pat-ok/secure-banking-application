package persistence;

import model.UserDatabase;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class JsonReaderTest extends JsonTest {

    @Test
    public void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            UserDatabase udb = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testReaderEmptyUserDatabase() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyUserDatabase.json");
        try {
            UserDatabase udb = reader.read();
            assertEquals(1, udb.getUserDatabase().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    public void testReaderGeneralUserDatabase() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralUserDatabase.json");
        try {
            UserDatabase udb = reader.read();
            checkAccount(udb,
                    "foo",
                    "pass123",
                    "Mr. Foo",
                    "100.00",
                    3,
                    2,
                    false);

            checkAccount(udb,
                    "bar",
                    "pass123",
                    "Mr. Bar",
                    "100.00",
                    3,
                    0,
                    false);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}

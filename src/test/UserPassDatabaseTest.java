import model.Account;
import model.UserDatabase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class UserPassDatabaseTest {
    private UserDatabase userDatabase;
    private HashMap<String, Account> testDB;


    @BeforeEach
    void runBefore() {
        userDatabase = new UserDatabase();
        testDB = userDatabase.getUserDatabase();
    }

    @Test
    void testConstructor() {
        assertEquals(2, testDB.size());
        assertTrue(testDB.containsKey("Foo") && testDB.containsKey("Bar"));
    }

    @Test
    void testStoreAccount() {
        Account testAccount = new Account("pass321", "Testing");
        userDatabase.storeAccount("Tester", testAccount);

        assertEquals(3,testDB.size());
        assertTrue(testDB.containsKey("Tester"));
    }

    @Test
    void testAuthUsername() {
        assertTrue(userDatabase.authUsername("Foo"));
        assertFalse(userDatabase.authUsername("FooFalse"));
        assertTrue(userDatabase.authUsername("Bar"));
        assertFalse(userDatabase.authUsername("BarFalse"));
        assertFalse(userDatabase.authUsername(""));
    }

    @Test
    void testAuthPassword() {
        assertTrue(userDatabase.authPassword("Foo", "pass123"));
        assertFalse(userDatabase.authPassword("Foo", "false"));
        assertTrue(userDatabase.authPassword("Bar", "pass123"));
        assertFalse(userDatabase.authPassword("Bar", "false"));
    }
}

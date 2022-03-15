package test;

import main.model.Account;
import main.model.UserPassDatabase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserPassDatabaseTest {
    private UserPassDatabase testdatabase;
    private HashMap<String, Account> testlogininfo;


    @BeforeEach
    void runBefore() {
        testdatabase = new UserPassDatabase();
        testlogininfo = testdatabase.getloginInfo();
        ArrayList<String> foogmailinbox = new ArrayList<>();
        foogmailinbox.add("This is Foo's inbox.");
        testlogininfo.put("foogmail",
                new Account("foo123",
                        "Mr. Foo",
                        500,
                        foogmailinbox));
    }

    @Test
    void testauthUsername() {
        assertTrue(testdatabase.authUsername("foogmail"));
        assertFalse(testdatabase.authUsername("false"));
    }

    @Test
    void testauthPassword() {
        assertFalse(testdatabase.authPassword("foogmail", "false"));
    }

    @Test
    void testinvalidEntry() {
        assertTrue(testdatabase.invalidEntry(""));
        assertTrue(testdatabase.invalidEntry(" "));
        assertTrue(testdatabase.invalidEntry(" Foo"));
        assertTrue(testdatabase.invalidEntry("Foo "));
        assertFalse(testdatabase.invalidEntry("Foo"));
    }

    @Test
    void testregisterPasswordMatch() {
        assertTrue(testdatabase.registerPasswordMatch("password", "password"));
        assertFalse(testdatabase.registerPasswordMatch("password", "false"));
    }
}

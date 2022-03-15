package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class UserPassDatabaseTest {
    private UserDatabase userDatabase;
    private HashMap<String, Account> testDB;


    @BeforeEach
    void runBefore() {
        userDatabase = new UserDatabase(true);
        testDB = userDatabase.getUserDatabase();
    }

    @Test
    void testConstructor() {
        assertEquals(2, testDB.size());
        assertTrue(testDB.containsKey("foo") && testDB.containsKey("bar"));
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
        assertTrue(userDatabase.authUsername("foo"));
        assertFalse(userDatabase.authUsername("fooFalse"));
        assertTrue(userDatabase.authUsername("bar"));
        assertFalse(userDatabase.authUsername("barFalse"));
        assertFalse(userDatabase.authUsername(""));
    }

    @Test
    void testAuthPassword() {
        assertTrue(userDatabase.authPassword("foo", "pass123"));
        assertFalse(userDatabase.authPassword("foo", "false"));
        assertTrue(userDatabase.authPassword("bar", "pass123"));
        assertFalse(userDatabase.authPassword("bar", "false"));
    }
}

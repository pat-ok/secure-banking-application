package model;

import exceptions.*;
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
    void testConstructorEmpty() {
        UserDatabase emptyDatabase = new UserDatabase(false);
        testDB = emptyDatabase.getUserDatabase();

        assertEquals(0, testDB.size());
        assertFalse(testDB.containsKey("foo") || testDB.containsKey("bar"));
    }

    @Test
    void testConstructorDemo() {
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
    void testIsUsernameFreeNo() {
        try {
            userDatabase.isUsernameFree("foo");
            fail("Username is already taken");
        } catch (UsernameNotFreeException unfe) {
            // pass
        }
    }

    @Test
    void testIsUsernameFreeNewNo() {
        try {
            userDatabase.storeAccount("newTester", new Account("pass123", "New Tester"));
            userDatabase.isUsernameFree("newTester");
            fail("Username is already taken");
        } catch (UsernameNotFreeException unfe) {
            // pass
        }
    }

    @Test
    void testIsUsernameFreeYes() {
        try {
            userDatabase.isUsernameFree("newTester");
            // pass
        } catch (UsernameNotFreeException unfe) {
            fail("Username should be free");
        }
    }

    @Test
    void testAuthUsernameNotFoundFoo() {
        try {
            userDatabase.authUsername("Foo");
            fail("Username is not in database");
        } catch (UsernameNotFoundException unfe) {
            // pass
        }
    }

    @Test
    void testAuthUsernameNotFoundBar() {
        try {
            userDatabase.authUsername("Bar");
            fail("Username is not in database");
        } catch (UsernameNotFoundException unfe) {
            // pass
        }
    }

    @Test
    void testAuthUsernameFoundFoo() {
        try {
            userDatabase.authUsername("foo");
            // pass
        } catch (UsernameNotFoundException unfe) {
            fail("Username should be in database");
        }
    }

    @Test
    void testAuthUsernameFoundBar() {
        try {
            userDatabase.authUsername("bar");
            // pass
        } catch (UsernameNotFoundException unfe) {
            fail("Username should be in database");
        }
    }

    @Test
    void testAuthPasswordFailWithThreeTries() {
        try {
            userDatabase.authPassword("foo", "fail", 3);
            fail("Password is not correct");
        } catch (IncorrectPasswordTriesLeftException iptle) {
            // pass
        } catch (IncorrectPasswordNoTriesLeftException ipntle) {
            fail("There are still tries left");
        }
    }

    @Test
    void testAuthPasswordFailWithTwoTries() {
        try {
            userDatabase.authPassword("foo", "fail", 2);
            fail("Password is not correct");
        } catch (IncorrectPasswordTriesLeftException iptle) {
            // pass
        } catch (IncorrectPasswordNoTriesLeftException ipntle) {
            fail("There are still tries left");
        }
    }

    @Test
    void testAuthPasswordFailFinalTry() {
        try {
            userDatabase.authPassword("foo", "fail", 1);
            fail("Password is not correct");
        } catch (IncorrectPasswordTriesLeftException iptle) {
            fail("There are no tries left");
        } catch (IncorrectPasswordNoTriesLeftException ipntle) {
            // pass
        }
    }

    @Test
    void testAuthPasswordFailWithTries() {
        try {
            userDatabase.authPassword("foo", "fail", 3);
            fail("Password is not correct");
        } catch (IncorrectPasswordTriesLeftException iptle) {
            // pass
        } catch (IncorrectPasswordNoTriesLeftException ipntle) {
            fail("There are still tries left");
        }
    }

    @Test
    void testAuthPasswordPassFoo() {
        try {
            userDatabase.authPassword("foo", "pass123", 3);
            // pass
        } catch (IncorrectPasswordException iptle) {
            fail("Password should pass");
        }
    }

    @Test
    void testAuthPasswordPassBar() {
        try {
            userDatabase.authPassword("bar", "pass123", 3);
            // pass
        } catch (IncorrectPasswordException iptle) {
            fail("Password should pass");
        }
    }
}

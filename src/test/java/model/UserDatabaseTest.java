package model;

import exceptions.authentication.AuthenticationFailedPasswordException;
import exceptions.authentication.AuthenticationFailedUsernameException;
import exceptions.registration.RegistrationFailedUsernameNotFreeException;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class UserDatabaseTest {
    private UserDatabase userDatabase;
    private HashMap<String, Account> testDB;


    @Before
    public void runBefore() {
        userDatabase = new UserDatabase(true);
        testDB = userDatabase.getUserDatabase();
    }

    @Test
    public void testConstructorEmpty() {
        UserDatabase emptyDatabase = new UserDatabase(false);
        testDB = emptyDatabase.getUserDatabase();

        assertEquals(1, testDB.size());
        assertTrue(testDB.containsKey("admin"));
        assertFalse(testDB.containsKey("foo") || testDB.containsKey("bar"));
    }

    @Test
    public void testConstructorDemo() {
        assertEquals(3, testDB.size());
        assertTrue(testDB.containsKey("foo") && testDB.containsKey("bar") && testDB.containsKey("admin"));
    }

    @Test
    public void testStoreAccount() {
        Account testAccount = new Account("pass321", "Testing");
        userDatabase.storeAccount("Tester", testAccount);

        assertEquals(4,testDB.size());
        assertTrue(testDB.containsKey("Tester"));
    }

    @Test
    public void testIsUsernameFreeNo() {
        try {
            userDatabase.isUsernameFree("foo");
            fail("Username is already taken");
        } catch (RegistrationFailedUsernameNotFreeException ex) {
            // pass
        }
    }

    @Test
    public void testIsUsernameFreeNewNo() {
        try {
            userDatabase.storeAccount("newTester", new Account("pass123", "New Tester"));
            userDatabase.isUsernameFree("newTester");
            fail("Username is already taken");
        } catch (RegistrationFailedUsernameNotFreeException ex) {
            // pass
        }
    }

    @Test
    public void testIsUsernameFreeYes() {
        try {
            userDatabase.isUsernameFree("newTester");
            // pass
        } catch (RegistrationFailedUsernameNotFreeException ex) {
            fail("Username should be free");
        }
    }

    @Test
    public void testAuthUsernameNotFoundFoo() {
        try {
            userDatabase.authUsername("Foo");
            fail("Username is not in database");
        } catch (AuthenticationFailedUsernameException ex) {
            // pass
        }
    }

    @Test
    public void testAuthUsernameNotFoundBar() {
        try {
            userDatabase.authUsername("Bar");
            fail("Username is not in database");
        } catch (AuthenticationFailedUsernameException ex) {
            // pass
        }
    }

    @Test
    public void testAuthUsernameFoundFoo() {
        try {
            userDatabase.authUsername("foo");
            // pass
        } catch (AuthenticationFailedUsernameException ex) {
            fail("Username should be in database");
        }
    }

    @Test
    public void testAuthUsernameFoundBar() {
        try {
            userDatabase.authUsername("bar");
            // pass
        } catch (AuthenticationFailedUsernameException ex) {
            fail("Username should be in database");
        }
    }

    @Test
    public void testAuthPasswordFail() {
        try {
            userDatabase.authPassword("foo", "fail");
            fail("Password is not correct");
        } catch (AuthenticationFailedPasswordException ex) {
            // pass
        }
    }

    @Test
    public void testAuthPasswordPassFoo() {
        try {
            userDatabase.authPassword("foo", "pass123");
            // pass
        } catch (AuthenticationFailedPasswordException ex) {
            fail("Password should pass");
        }
    }

    @Test
    public void testAuthPasswordPassBar() {
        try {
            userDatabase.authPassword("bar", "pass123");
            // pass
        } catch (AuthenticationFailedPasswordException ex) {
            fail("Password should pass");
        }
    }
}

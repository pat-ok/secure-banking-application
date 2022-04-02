package model;

import exceptions.authentication.AuthenticationFailedPasswordException;
import exceptions.authentication.AuthenticationFailedUsernameException;
import exceptions.registration.RegistrationFailedUsernameNotFreeException;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static model.Security.hashFunction;
import static model.Security.salt;

// Represents database of registered accounts being stored with the key being the username
public class UserDatabase implements Writable {
    private final HashMap<String, Account> databaseInfo;

    // REQUIRES: nothing
    // MODIFIES: this is a constructor
    // EFFECTS: constructs new database with admin account and demo status,
    //          if true, adds two accounts for demo purposes
    public UserDatabase(boolean demo) {
        databaseInfo = new HashMap<>();

        if (demo) {

            // adding Foo
            String saltFoo = salt();
            String passFoo = saltFoo + hashFunction(saltFoo + "pass123");
            databaseInfo.put("foo", new Account(passFoo, "Mr. Foo"));

            // adding Bar
            String saltBar = salt();
            String passBar = saltBar + hashFunction(saltBar + "pass123");
            databaseInfo.put("bar", new Account(passBar, "Mr. Bar"));
        }

        // adding admin account
        databaseInfo.put("admin", new Account("admin", "Admin"));
    }

    // getter for database
    public HashMap<String, Account> getUserDatabase() {
        return databaseInfo;
    }

    // REQUIRES: nothing
    // MODIFIES: this
    // EFFECTS: adds new account with their registered username to database
    public void storeAccount(String newUser, Account newAccount) {
        databaseInfo.put(newUser, newAccount);
    }

    // REQUIRES: username must not be registered already
    // MODIFIES: nothing
    // EFFECTS: nothing
    public void isUsernameFree(String username) throws RegistrationFailedUsernameNotFreeException {
        if (databaseInfo.containsKey(username)) {
            throw new RegistrationFailedUsernameNotFreeException();
        }
    }

    // REQUIRES: username must be registered
    // MODIFIES: nothing
    // EFFECTS: nothing
    public void authUsername(String username) throws AuthenticationFailedUsernameException {
        if (!databaseInfo.containsKey(username)) {
            throw new AuthenticationFailedUsernameException();
        }
    }

    // REQUIRES: password must match stored password for account and there must be more than 1 try left
    // MODIFIES: nothing
    // EFFECTS: nothing
    public void authPassword(String username, String password) throws AuthenticationFailedPasswordException {
        String salt = databaseInfo.get(username).getPassword().substring(0, 5);
        int saltedPass = hashFunction(salt + password);
        if (!databaseInfo.get(username).getPassword().equals(salt + saltedPass)) {
            throw new AuthenticationFailedPasswordException();
        }
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        List<String> keys = new ArrayList<>(databaseInfo.keySet());
        List<Account> values = new ArrayList<>(databaseInfo.values());
        json.put("keys", keys);
        json.put("values", values);
        return json;
    }
}

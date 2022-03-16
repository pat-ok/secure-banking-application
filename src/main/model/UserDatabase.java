package model;

import exceptions.IncorrectPasswordNoTriesLeftException;
import exceptions.IncorrectPasswordTriesLeftException;
import exceptions.UsernameNotFoundException;
import exceptions.UsernameNotFreeException;
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
    // EFFECTS: constructs new database,
    //          and if true, adds two accounts for demo purposes
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
    public void isUsernameFree(String username) throws UsernameNotFreeException {
        if (databaseInfo.containsKey(username)) {
            throw new UsernameNotFreeException();
        }
    }

    // REQUIRES: username must be registered
    // MODIFIES: nothing
    // EFFECTS: nothing
    public void authUsername(String username) throws UsernameNotFoundException {
        if (!databaseInfo.containsKey(username)) {
            throw new UsernameNotFoundException();
        }
    }

    // REQUIRES: password must match stored password for account and there must be more than 1 try left
    // MODIFIES: nothing
    // EFFECTS: nothing
    public void authPassword(String username, String password, int tries)
            throws IncorrectPasswordTriesLeftException, IncorrectPasswordNoTriesLeftException {
        String salt = databaseInfo.get(username).getPassword().substring(0, 5);
        int saltedPass = hashFunction(salt + password);
        if (!databaseInfo.get(username).getPassword().equals(salt + saltedPass)) {
            if (tries > 1) {
                throw new IncorrectPasswordTriesLeftException(tries);
            }
            throw new IncorrectPasswordNoTriesLeftException();
        }
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        List<String> keys = new ArrayList<>(databaseInfo.keySet());
        List<Account> accounts = new ArrayList<>(databaseInfo.values());
        json.put("keys", keys);
        json.put("values", accounts);
        return json;
    }
}
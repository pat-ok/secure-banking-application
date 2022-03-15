package model;

import java.util.HashMap;

import static model.Security.hashFunction;
import static model.Security.salt;

// Represents database of registered accounts being stored with the key being the username
public class UserDatabase {
    private final HashMap<String, Account> database;

    // REQUIRES: nothing
    // MODIFIES: this is a constructor
    // EFFECTS: constructs new empty database,
    //          adds two accounts for demo purposes
    public UserDatabase() {
        database = new HashMap<>();

        // adding Foo
        String saltFoo = salt();
        String passFoo = saltFoo + hashFunction(saltFoo + "pass123");
        database.put("Foo", new Account(passFoo, "Mr. Foo"));

        // adding Bar
        String saltBar = salt();
        String passBar = saltBar + hashFunction(saltBar + "pass123");
        database.put("Bar", new Account(passBar, "Mr. Bar"));
    }

    // getter for database
    public HashMap<String, Account> getUserDatabase() {
        return database;
    }

    // REQUIRES: nothing
    // MODIFIES: this
    // EFFECTS: adds new account with their registered username to database
    public void storeAccount(String newUser, Account newAccount) {
        database.put(newUser, newAccount);
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: returns true if username is found in list of registered accounts
    public boolean authUsername(String username) {
        return database.containsKey(username);
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: returns true if hashed password matches hash stored on the account
    public boolean authPassword(String username, String password) {
        String salt = database.get(username).getPassword().substring(0, 5);
        int saltedPass = hashFunction(salt + password);
        return database.get(username).getPassword().equals(salt + saltedPass);
    }
}

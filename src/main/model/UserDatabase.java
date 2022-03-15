package model;

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
    public UserDatabase(boolean bool) {
        databaseInfo = new HashMap<>();

        if (bool) {

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

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: returns true if username is found in list of registered accounts
    public boolean authUsername(String username) {
        return databaseInfo.containsKey(username);
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: returns true if hashed password matches hash stored on the account
    public boolean authPassword(String username, String password) {
        String salt = databaseInfo.get(username).getPassword().substring(0, 5);
        int saltedPass = hashFunction(salt + password);
        return databaseInfo.get(username).getPassword().equals(salt + saltedPass);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        List<String> keys = new ArrayList<String>(databaseInfo.keySet());
        List<Account> accounts = new ArrayList<Account>(databaseInfo.values());
        json.put("keys", keys);
        json.put("values", accounts);
        return json;
    }
}

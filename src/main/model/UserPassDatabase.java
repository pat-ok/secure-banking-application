package main.model;

import java.util.ArrayList;
import java.util.HashMap;

// Represents database of registered accounts being stored
public class UserPassDatabase {

    private HashMap<String, Account> loginInfo = new HashMap<String, Account>();

    // getter
    public HashMap getloginInfo() {
        return loginInfo;
    }

    //
    public UserPassDatabase() {
        ArrayList<String> fooinbox = new ArrayList<>();
        fooinbox.add("This is Mr. Foo's inbox. For testing purposes.");
        loginInfo.put("foo",
                new Account("foo123",
                        "Mr. Foo",
                        500,
                        fooinbox));
        ArrayList<String> barinbox = new ArrayList<>();
        barinbox.add("This is Mr. Bar's inbox.");
        loginInfo.put("bar",
                new Account("bar123",
                        "Mr. Bar",
                        500,
                        barinbox));
    }

    // REQUIRES: username to be a valid entry
    // EFFECTS: returns true if username is found in list of registered accounts
    public boolean authUsername(String username) {
        return loginInfo.containsKey(username);
    }

    // REQUIRES: username to have been confirmed for existence
    // EFFECTS: returns true if username and hashed password matches username and hash stored on the account
    public boolean authPassword(String username, String password) {
//        Account temp = (Account) loginInfo.get(username);
//        return temp.getPassword().equals(password);
        String salt = loginInfo.get(username).getPassword().substring(0, 5);
        int saltedpass = main.ui.BankingApp.hashFunction(salt + password);
        return loginInfo.get(username).getPassword().equals(salt + saltedpass);
    }

    // EFFECTS: returns true if entry has illegal traits:
    //          empty
    //          only whitespace
    //          leading or trailing spaces
    public boolean invalidEntry(String s) {
        return s.matches(".{0}|( )*|( )+.*( )*|( )*.*( )+");
    }

    // REQUIRES: both string parameters must be valid entries
    // EFFECTS: checks if new password is confirmed to be the same as second entry
    public boolean registerPasswordMatch(String newPass, String newPassConfirm) {
        return newPass.equals(newPassConfirm);
    }

    // REQUIRES: username, password, and name must be valid entries
    // MODIFIES: this
    // EFFECTS: stores a new account in the database with username as key to hashmap
    public void storeAccount(String newUser, Account newAccount) {
        loginInfo.put(newUser, newAccount);
    }

}

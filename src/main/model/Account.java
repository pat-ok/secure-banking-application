package main.model;

import java.util.ArrayList;

// Represents an account that has a password, a name, a balance, and an ArrayList of string messages.
public class Account {
    private String password;                    // password associated with key username in UserPassDatabase
    private String name;                        // name of account owner
    private double balance;                     // current balance of account owner
    private ArrayList<String> messages;         // list of notification messages received


    // REQUIRES: account password and account name must be valid entries
    //           (non-empty, not only consisting of spaces, no leading or trailing spaces)
    // EFFECTS: password on account is set to accountPass,
    //          name on account is set to accountName,
    //          balance on account is set to accountBalance,
    //          messages on account is set to accountMessages.
    public Account(String accountPass, String accountName, double accountBalance, ArrayList<String> accountMessages) {
        password = accountPass;
        name = accountName;
        balance = accountBalance;
        messages = accountMessages;
    }

    // getter
    public String getPassword() {
        return password;
    }

    // getter
    public String getName() {
        return name;
    }

    // getter
    public double getBalance() {
        return balance;
    }

    // getter
    public ArrayList<String> getInbox() {
        return messages;
    }

    // REQUIRES: amount >= 0
    // MODIFIES: account balance
    // EFFECTS: deposited amount is added to current bank balance
    public double deposit(double amount) {
        balance += amount;
        return balance;
    }

    // REQUIRES: amount >= balance
    // MODIFIES: account balance
    // EFFECTS: withdrawn amount is subtracted from current bank balance
    public double withdraw(double amount) {
        balance -= amount;
        return balance;
    }

}

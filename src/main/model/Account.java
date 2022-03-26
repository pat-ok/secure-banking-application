package model;

import ui.Options;

import java.math.BigDecimal;
import java.util.ArrayList;

import static model.Formatting.currencyFormat;

// Represents an account that has a password, a name, a balance, and an ArrayList of string messages.
public class Account extends Options {
    private final String password;                       // password for the account
    private final String name;                           // name of account owner
    private BigDecimal balance;                          // current balance of account owner
    private final ArrayList<String> notifications;       // stack of notification messages received
    private final ArrayList<String> transactions;        // stack of transactions recorded
    private boolean lock;                                // account locking status

    // REQUIRES: account password and account name must be valid entries
    //           (non-empty, not only consisting of spaces, no leading or trailing spaces)
    // MODIFIES: this is a constructor
    // EFFECTS: constructs new account with...
    //          password on account is set to accountPass,
    //          name on account is set to accountName,
    //          balance on account is initialized at 100 with signup bonus,
    //          inbox is initialized with welcome messages,
    //          transactions is initialized with empty history
    //          account is unlocked
    public Account(String accountPass, String accountName) {
        this.password = accountPass;
        this.name = accountName;
        this.balance = new BigDecimal("100");
        this.notifications = new ArrayList<>();
        this.notifications.add("Welcome to BigDecimal Bank of Canada! Thank you for choosing to bank with us.");
        this.notifications.add("Enjoy our arbitrary-precision signed decimal numbers, so bits don't drop your coins!");
        this.notifications.add("You have received a $100 sign up bonus!");
        this.transactions = new ArrayList<>();
        this.lock = false;
    }

    // REQUIRES: nothing
    // MODIFIES: this is a constructor
    // EFFECTS: secondary constructor for JsonReader that constructs an account with...
    //          password is set to pass,
    //          name is set to name,
    //          balance is set to balance,
    //          notifications is set to notifications,
    //          transactions is set to transactions
    public Account(String pass,
                   String name,
                   String balance,
                   ArrayList<String> notifications,
                   ArrayList<String> transactions) {
        this.password = pass;
        this.name = name;
        this.balance = new BigDecimal(balance.replaceAll("\\$", ""));
        this.notifications = notifications;
        this.transactions = transactions;
        this.lock = false;
    }

    // getter for password
    public String getPassword() {
        return password;
    }

    // getter for name
    public String getName() {
        return name;
    }

    // getter for balance as big decimal
    public BigDecimal getBalance() {
        return balance;
    }

    // getter for balance as string in currency format
    public String getBalanceString() {
        return currencyFormat(balance);
    }

    // getter for notifications
    public ArrayList<String> getNotifications() {
        return notifications;
    }

    // getter for notifications as string
    public String getNotificationsString() {
        int n = 1;
        String temp = "There are a total of " + notifications.size() + " new notification(s).\n\n";
        StringBuilder welcomeMessage = new StringBuilder(temp);
        for (String s : notifications) {
            String num = "[" + n + "] ";
            welcomeMessage.append(num).append(s).append("\n\n");
            n++;
        }
        return welcomeMessage.toString();
    }

    // getter for transactions
    public ArrayList<String> getTransactions() {
        return transactions;
    }

    // getter for transactions as string


    // getter for locked status
    public boolean getLock() {
        return lock;
    }


    // REQUIRES: nothing (checked that amount is >= 0)
    // MODIFIES: this
    // EFFECTS: deposited amount is added to current bank balance,
    //          transaction is recorded,
    //          and returns string denoting deposit amount and updated bank balance
    public String deposit(String amount) {
        BigDecimal deposit = new BigDecimal(amount);
        balance = balance.add(deposit);
        transactions.add("Deposit: " + currencyFormat(deposit) + "\n     Balance: " + currencyFormat(balance));
        return "You have deposited: " + currencyFormat(deposit)
                + ". Your new balance is: " + currencyFormat(balance);
    }

    // REQUIRES: nothing (checked that amount >= balance)
    // MODIFIES: this
    // EFFECTS: withdrawn amount is subtracted from current bank balance,
    //          transaction is recorded,
    //          and returns string denoting withdrawal amount and updated bank balance
    public String withdraw(String amount) {
        BigDecimal withdraw = new BigDecimal(amount);
        balance = balance.subtract(withdraw);
        transactions.add("Withdrawal: " + currencyFormat(withdraw) + "\n     Balance: " + currencyFormat(balance));
        return "You have withdrawn: " + currencyFormat(withdraw)
                + ". Your new balance is: " + currencyFormat(balance);
    }

    // REQUIRES: nothing
    // MODIFIES: this
    // EFFECTS: returns string containing welcome message and all notifications,
    //          and clears the notifications that have been displayed
    public String loginNotifications() {
        int n = 1;
        String temp = "You have: " + notifications.size() + " new notification(s).\n\n";
        StringBuilder welcomeMessage = new StringBuilder(temp);
        for (String s : notifications) {
            String num = "[" + n + "] ";
            welcomeMessage.append(num).append(s).append("\n\n");
            n++;
        }
        notifications.clear();
        return welcomeMessage.toString();
    }

    // REQUIRES: nothing (checked for valid user and sufficient funds)
    // MODIFIES: this
    // EFFECTS: subtracts outgoing transfer amount from balance,
    //          adds eTransfer to transaction history
    public void transferOut(String amount, String recipient) {
        BigDecimal outgoingAmount = new BigDecimal(amount);
        balance = balance.subtract(outgoingAmount);
        transactions.add("Outgoing eTransfer: " + currencyFormat(outgoingAmount) + " to " + recipient
                + "\n     Balance: " + currencyFormat(balance));
    }

    // REQUIRES: nothing
    // MODIFIES: this
    // EFFECTS: adds incoming transfer amount to balance,
    //          adds eTransfer message notification to inbox,
    //          adds eTransfer to transaction history
    public void transferIn(String amount, String senderName) {
        BigDecimal incomingAmount = new BigDecimal(amount);
        balance = balance.add(incomingAmount);
        notifications.add("You have received an Interac eTransfer of " + currencyFormat(incomingAmount) + " from "
                + senderName + ".");
        transactions.add("Incoming eTransfer: " + currencyFormat(incomingAmount) + " from " + senderName
                + "\n     Balance: " + currencyFormat(balance));
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: returns string denoting complete history of transactions
    public String transactionHistory() {
        int i = 1;
        StringBuilder transactionHistory = new StringBuilder("Transaction history for " + name + ":\n\n");
        if (transactions.size() == 0) {
            transactionHistory.append("No transactions to show.");
        } else {
            for (String transaction : transactions) {
                String num = "[" + i + "] ";
                transactionHistory.append(num).append(transaction).append("\n\n");
                i++;
            }
        }
        return transactionHistory.toString();
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: sets locked status to true
    public void lockAccount() {
        this.lock = true;
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: sets locked status to false
    public void unlockAccount() {
        this.lock = false;
    }
}

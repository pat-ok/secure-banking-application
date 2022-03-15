package main.ui;

import main.model.Account;
import main.model.UserPassDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


// Banking Application
public class BankingApp {

    private Scanner input;

    // main menu commands
    private static final String LOGIN_COMMAND = "login";
    private static final String REGISTER_COMMAND = "register";
    private static final String HACK_COMMAND = "hack";
    private static final String QUIT_COMMAND = "quit";

    // account login commands
    private static final String CHECK_BALANCE_COMMAND = "balance";
    private static final String CHECK_INBOX_COMMAND = "checkinbox";
    private static final String DEPOSIT_COMMAND = "deposit";
    private static final String WITHDRAW_COMMAND = "withdraw";
    private static final String TRANSFER_COMMAND = "transfer";
    private static final String DELETE_INBOX_COMMAND = "deleteinbox";
    private static final String LOGOUT_COMMAND = "logout";

    static UserPassDatabase database = new UserPassDatabase();
    static HashMap<String, Account> accountInfo = database.getloginInfo();

    static String username = null;
    static String password = null;
    static String newUsername = null;
    static String newPassword = null;
    static String newPasswordConfirm = null;
    static String newName = null;

    // EFFECTS: initiates Banking Application
    public BankingApp() {
        mainMenu();
    }

    // EFFECTS: main menu processes user input
    private void mainMenu() {
        Boolean runApp = true;
        String command = null;
        input = new Scanner(System.in);

        while (runApp) {
            displayOptions();
            command = input.nextLine();
            command = command.toLowerCase();

            if (command.equals(QUIT_COMMAND)) {
                runApp = false;
            } else {
                parseCommand(command);
            }
        }
        System.out.println("Bye.");
    }

    // EFFECTS: displays main menu options
    private void displayOptions() {
        System.out.println("Welcome to TD Canada Trust. How can we help you today?");
        System.out.println("Select from:  ["
                + LOGIN_COMMAND + " | "
                + REGISTER_COMMAND + " | "
                + HACK_COMMAND + " | "
                + QUIT_COMMAND + "]");
    }

    private void displayInfo() {
        System.out.println("Enter '" + LOGIN_COMMAND + "' to login.");
        System.out.println("Enter '" + REGISTER_COMMAND + "' to register.");
        System.out.println("Enter '" + HACK_COMMAND + "' to hack.");
        System.out.println("Enter '" + QUIT_COMMAND + "' at any time to quit.");
    }

    // EFFECTS: parses commands from user on main menu
    private void parseCommand(String command) {
        if (command.equals(LOGIN_COMMAND)) {
            doLogin();
        } else if (command.equals(REGISTER_COMMAND)) {
            doRegister();
        } else if (command.equals(HACK_COMMAND)) {
            doHack();
        } else if (command.equals("hash")) {
            System.out.println("For prototype purposes");
            System.out.println("Enter the string you would like to hash");
            System.out.println(hashFunction(input.nextLine()));
            System.out.println("And the random salt generated is:");
            System.out.println(salt());
        } else {
            System.out.println("Command is not valid. Please try again.");
        }
    }


    // EFFECTS: initiates login UI and allows users to enter username
    private void doLogin() {
        System.out.println("Please enter your username.");
        username = input.nextLine();
        if (username.equals(QUIT_COMMAND)) {
            System.out.println("Returning to main menu.");
        } else if (database.authUsername(username)) {
            System.out.println("Username exists.");
            loginPasswordUI();
        } else {
            System.out.println("Username DNE. Try again.");
            doLogin();
        }
    }

    // EFFECTS: allows user to enter password
    private void loginPasswordUI() {
        System.out.println("Please enter your password.");
        Integer remainingTries = 3;
        while (remainingTries > 0) {
            password = input.nextLine();
            if (password.equals(QUIT_COMMAND)) {
                remainingTries = 0;
                System.out.println("Returning to main menu.");
            } else if (database.authPassword(username, password)) {
                Account userAccount = (Account) database.getloginInfo().get(username);
                System.out.println("Password matches username. Access successful. \nWelcome to TD, "
                        + userAccount.getName() + "!");
                accountMenu();
            } else if (remainingTries == 1) {
                System.out.println("Login failed. Account locked for 10 seconds.\nLaw enforcement has been called.");
                remainingTries--;
                countdownTimer(10);
            } else {
                remainingTries--;
                System.out.println("Password is incorrect. You have " + remainingTries + " attempt(s) left.");
            }
        }
    }


    // EFFECTS: initiates registration UI
    private void doRegister() {
        System.out.println("Please enter your preferred name.");
        newName = input.nextLine();
        if (newName.equals(QUIT_COMMAND)) {
            System.out.println("Returning to main menu.");
        } else if (database.invalidEntry(newName)) {
            System.out.println("Name is invalid.");
            doRegister();
        } else {
            registerUsernameUI();
        }
    }


    // EFFECTS: allows users to register for a username
    private void registerUsernameUI() {
        System.out.println("Please enter your desired username. \nNote: username cannot be '" + QUIT_COMMAND + "'.");
        newUsername = input.nextLine();
        if (newUsername.equals(QUIT_COMMAND)) {
            System.out.println("Returning to main menu.");
        } else if (database.invalidEntry(newUsername) || database.authUsername(newUsername)) {
            System.out.println("Username is invalid or already exists.");
            registerUsernameUI();
        } else {
            System.out.println("Username is valid.");
            registerPasswordUI();
        }
    }

    // EFFECTS: allows user to register for a password
    private void registerPasswordUI() {
        System.out.println("Please enter your desired password.");
        newPassword = input.nextLine();
        if (database.invalidEntry(newPassword)) {
            System.out.println("Password is invalid.");
            registerPasswordUI();
        } else {
            System.out.println("Please confirm your password.");
            newPasswordConfirm = input.nextLine();
            if (database.registerPasswordMatch(newPassword, newPasswordConfirm)) {
                ArrayList<String> inbox = new ArrayList<>();
                inbox.add("Welcome to TD, " + newName.substring(0,1).toUpperCase() + newName.substring(1)
                        + "!\nWe are glad to have you as a customer.");
                inbox.add("Thank you for choosing TD Bank of Canada. \nPlease review your terms and conditions.");
                String salt = salt();
                database.storeAccount(newUsername, new Account(salt + hashFunction(salt + newPassword),
                        newName.substring(0,1).toUpperCase() + newName.substring(1), 0, inbox));
                System.out.println("Account has been registered. Thank you!");
            } else {
                System.out.println("Passwords do not match. Please try again.");
                registerPasswordUI();
            }
        }
    }

    // EFFECTS: prints all usernames and passwords on database
    private void doHack() {
        System.out.println("*playing Mission Impossible theme*");
        System.out.println("Hacking the mainframe...");
        countdownTimer(5);
        System.out.println("AND WE'RE IN! \n");
        accountInfo.forEach((k,v)
                -> System.out.println("Username: " + k + "  "
                + "Password: " + v.getPassword()));
        System.out.println("Enter '" + QUIT_COMMAND + "' to return to main menu.");
        String end = input.next();
    }

    // EFFECTS: runs account menu UI once logged in
    private void accountMenu() {
        Boolean runAccountMenu = true;
        String accountCommand = null;
        input = new Scanner(System.in);

        while (runAccountMenu) {
            displayAccountOptions();
            accountCommand = input.nextLine();
            accountCommand = accountCommand.toLowerCase();

            if (accountCommand.equals(LOGOUT_COMMAND)) {
                runAccountMenu = false;
            } else {
                parseAccountCommand(accountCommand);
            }
        }
        System.out.println("You have been logged out. Press quit to return to main menu.");
    }

    // EFFECTS: prints all options available once logged into account
    private void displayAccountOptions() {
        System.out.println("Select from:  ["
                + CHECK_BALANCE_COMMAND + " | "
                + CHECK_INBOX_COMMAND + " | "
                + DEPOSIT_COMMAND + " | "
                + WITHDRAW_COMMAND + " | "
                + TRANSFER_COMMAND + " | "
                + DELETE_INBOX_COMMAND + " | "
                + LOGOUT_COMMAND + "]");
    }

    private void displayAccountInfo() {
        System.out.println("Enter '" + CHECK_BALANCE_COMMAND + "' to check balance.");
        System.out.println("Enter '" + CHECK_INBOX_COMMAND + "' to check messages.");
        System.out.println("Enter '" + DEPOSIT_COMMAND + "' to deposit money.");
        System.out.println("Enter '" + WITHDRAW_COMMAND + "' to withdraw money.");
        System.out.println("Enter '" + TRANSFER_COMMAND + "' to eTransfer money.");
        System.out.println("Enter '" + DELETE_INBOX_COMMAND + "' to delete messages.");
        System.out.println("Enter '" + LOGOUT_COMMAND + "' to logout.");
    }

    // EFFECTS: parses commands once logged in.
    private void parseAccountCommand(String command) {
        if (command.equals(CHECK_BALANCE_COMMAND)) {
            doCheckBalance();
        } else if (command.equals(CHECK_INBOX_COMMAND)) {
            doCheckInbox();
        } else if (command.equals(DEPOSIT_COMMAND)) {
            doDeposit();
        } else if (command.equals(WITHDRAW_COMMAND)) {
            doWithdraw();
        } else if (command.equals(TRANSFER_COMMAND)) {
            doTransfer();
        } else if (command.equals(DELETE_INBOX_COMMAND)) {
            doDeleteInbox();
        } else {
            System.out.println("Command is not valid. Please try again.");
        }
    }

    // EFFECTS: prints current bank balance
    private void doCheckBalance() {
        Account userAccount = (Account) database.getloginInfo().get(username);
        System.out.println("Your current balance is: $" + userAccount.getBalance());
        System.out.println("");
    }

    // EFFECTS: reads messages from inbox
    private void doCheckInbox() {
        Account userAccount = (Account) database.getloginInfo().get(username);
        System.out.println("You currently have: " + userAccount.getInbox().size() + " message(s).");
        Integer n = 1;
        for (String x : userAccount.getInbox()) {
            System.out.println("[" + n + "]: '" + x + "'");
            n++;
        }
        System.out.println("");
    }

    // EFFECTS: deposits money to bank account
    private void doDeposit() {
        Account userAccount = (Account) database.getloginInfo().get(username);
        System.out.println("Deposit amount: $");
        Double amount = Double.parseDouble(input.nextLine());
        userAccount.deposit(amount);
        System.out.println("You have deposited: $" + amount + ".");
        System.out.println("Your new balance is: $" + userAccount.getBalance() + ".");
        System.out.println("");
    }

    // EFFECTS: withdraws money from bank account
    private void doWithdraw() {
        Account userAccount = (Account) database.getloginInfo().get(username);
        System.out.println("Withdraw amount: $");
        Double amount = Double.parseDouble(input.nextLine());
        if (amount > userAccount.getBalance()) {
            System.out.println("You have insufficient funds.");
        } else {
            userAccount.withdraw(amount);
            System.out.println("You have withdrawn $" + amount + ".");
            System.out.println("Your new balance is $" + userAccount.getBalance() + ".");
        }
        System.out.println("");
    }

    // EFFECTS: transfers an amount of money to another valid registered user
    private void doTransfer() {
        Account userAccount = (Account) database.getloginInfo().get(username);
        System.out.println("Recipient username: ");
        String recipientuser = input.nextLine();
        if (database.authUsername(recipientuser)) {
            System.out.println("Transfer amount: $");
            Double transferamount = Double.parseDouble(input.nextLine());
            if (transferamount > userAccount.getBalance()) {
                System.out.println("You have insufficient funds.");
            } else {
                Account recipientAccount = (Account) database.getloginInfo().get(recipientuser);
                userAccount.withdraw(transferamount);
                recipientAccount.deposit(transferamount);
                recipientAccount.getInbox().add("You have received an eTransfer from " + userAccount.getName()
                        + " of $" + transferamount);
                System.out.println(recipientuser + " has received your eTransfer!");
            }
        } else {
            System.out.println("User not found. Returning to menu.");
        }
        System.out.println("");
    }

    // EFFECTS: deletes inbox messages
    private void doDeleteInbox() {
        Account userAccount = (Account) database.getloginInfo().get(username);
        System.out.println("Are you sure? y/n");
        String confirm = input.nextLine();
        if (confirm.equals("y")) {
            userAccount.getInbox().clear();
            System.out.println("Inbox has been cleared.");
        } else if (confirm.equals("n")) {
            System.out.println("Action has been cancelled.");
        } else {
            System.out.println("Sorry, command not recognized.");
        }
        System.out.println("");
    }

    // REQUIRES: String must be string
    // EFFECTS: String is scrambled into a random integer
    public static int hashFunction(String s) {
        int hash = 604;
        for (int i = 0; i < s.length(); i++) {
            hash = hash * 17 + s.charAt(i);
        }
        return hash;
    }

    // EFFECTS: creates a random string of length 5
    public static String salt() {
        String alphanumeric = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder(5);
        for (int i = 0; i < 5; i++) {
            sb.append(alphanumeric.charAt((int) (alphanumeric.length() * Math.random())));
        }
        return sb.toString();
    }

    // REQUIRES: Time is not empty
    // EFFECTS: A countdown is initiated in seconds from given integer time. Prints each second.
    public static Object countdownTimer(Integer time) {
//        Integer timer = time;
        while (time > 0) {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                System.out.println(e);
            }
            System.out.println(time + "...");
            time--;
        }
        return null;
    }

}

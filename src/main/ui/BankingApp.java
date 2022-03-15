package ui;

import model.Account;
import model.UserDatabase;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Scanner;

import static model.Security.*;
import static model.Formatting.*;

// Represents the Banking Application
public class BankingApp extends Options {
    //private static final String JSON_STORE = "./data/database.json";
    private Scanner input;

    static UserDatabase database = new UserDatabase();
    static HashMap<String, Account> databaseInfo = database.getUserDatabase();

    static String username;
    static String password;
    static String newUsername;
    static String newPassword;
    static String newPasswordConfirm;
    static String newName;

    // EFFECTS: initiates Banking Application
    public BankingApp() {
        mainMenu();
    }

    // EFFECTS: main menu processes user input
    private void mainMenu() {
        boolean runApp = true;
        String command;
        input = new Scanner(System.in);

        while (runApp) {
            displayMainOptions();
            command = pretty(input.nextLine());
            if (command.equals(QUIT_COMMAND)) {
                runApp = false;
            } else {
                parseCommand(command);
            }
        }
        System.out.println("Goodbye!");
    }

    // EFFECTS: parses commands from user on main menu
    private void parseCommand(String command) {
        switch (command) {
            case LOGIN_COMMAND:
                doLogin();
                break;
            case REGISTER_COMMAND:
                doRegister();
                break;
            case HACK_COMMAND:
                doHack();
                break;
            case "hash":
                System.out.println("For demonstration purposes. Enter the string you would like to hash.");
                System.out.println(hashFunction(input.nextLine()));
                System.out.println("And a random salt generated is:" + salt());
                break;
            default:
                System.out.println("Command is not valid. Please try again.");
                break;
        }
    }

    // EFFECTS: initiates login sequence and verifies entered username
    private void doLogin() {
        System.out.println("Please enter your username.");
        username = input.nextLine();
        if (username.equals(QUIT_COMMAND)) {
            Options.returnMain();
        } else if (database.authUsername(username)) {
            System.out.println("Username exists.");
            doLoginPassword();
        } else {
            System.out.println("Username DNE. Try again.");
            doLogin();
        }
    }

    // EFFECTS: continues login sequence and verifies entered password
    private void doLoginPassword() {
        System.out.println("Please enter your password.");
        int remainingTries = 3;
        while (remainingTries > 0) {
            password = input.nextLine();
            if (password.equals(QUIT_COMMAND)) {
                remainingTries = 0;
                returnMain();
            } else if (database.authPassword(username, password)) {
                Account userAccount = databaseInfo.get(username);
                System.out.println(userAccount.loginNotifications());
                accountMenu();
            } else if (remainingTries == 1) {
                System.out.println("Login failed. Account locked for 5 seconds.\nLaw enforcement has been called.");
                remainingTries--;
                countdownTimer();
            } else {
                remainingTries--;
                System.out.println("Password is incorrect. You have " + remainingTries + " attempt(s) left.");
            }
        }
    }

    // EFFECTS: initiates registration sequence and allows users to enter a name
    private void doRegister() {
        System.out.println("Please enter your preferred name.");
        newName = input.nextLine();
        if (newName.equals(QUIT_COMMAND)) {
            returnMain();
        } else if (isInvalidEntry(newName)) {
            System.out.println("Name is invalid.");
            doRegister();
        } else {
            registerUsername();
        }
    }

    // EFFECTS: continues registration sequence and allows users to enter a username
    private void registerUsername() {
        System.out.println("Please enter your desired username.\nNote: cannot use '" + QUIT_COMMAND + "'.");
        newUsername = input.nextLine();
        if (newUsername.equals(QUIT_COMMAND)) {
            returnMain();
        } else if (isInvalidEntry(newUsername) || database.authUsername(newUsername)) {
            System.out.println("Username is invalid or already exists.");
            registerUsername();
        } else {
            System.out.println("Username is valid.");
            registerPassword();
        }
    }

    // EFFECTS: continues registration sequence and allows users to enter a password
    private void registerPassword() {
        System.out.println("Please enter your desired password.");
        newPassword = input.nextLine();
        if (isInvalidEntry(newPassword)) {
            System.out.println("Password is invalid.");
            registerPassword();
        } else {
            System.out.println("Please confirm your password.");
            newPasswordConfirm = input.nextLine();
            if (newPassword.equals(newPasswordConfirm)) {
                String salt = salt();
                database.storeAccount(newUsername,
                        new Account(salt + hashFunction(salt + newPassword), capitalizeName(newName)));
                System.out.println("Account has been registered. Thank you!");
            } else {
                System.out.println("Passwords do not match. Please try again.");
                registerPassword();
            }
        }
    }

    // EFFECTS: prints all usernames and passwords on database
    private void doHack() {
        System.out.println("*playing Mission Impossible theme*");
        System.out.println("Hacking the mainframe...");
        countdownTimer();
        System.out.println("AND WE'RE IN! \n");
        databaseInfo.forEach((k, v)
                -> System.out.println("Username: " + k + "  "
                + "Password: " + v.getPassword()));
    }

    // EFFECTS: account menu processes user input after logging in
    private void accountMenu() {
        boolean runAccountMenu = true;
        String accountCommand;
        input = new Scanner(System.in);

        while (runAccountMenu) {
            displayAccountOptions();
            accountCommand = pretty(input.nextLine());
            if (accountCommand.equals(LOGOUT_COMMAND)) {
                runAccountMenu = false;
            } else {
                parseAccountCommand(accountCommand);
            }
        }
        System.out.println("You have been logged out. Press quit to return to main menu.");
    }

    // EFFECTS: parses commands once logged in
    private void parseAccountCommand(String command) {
        switch (command) {
            case CHECK_BALANCE_COMMAND:
                doCheckBalance();
                break;
            case DEPOSIT_COMMAND:
                doDeposit();
                break;
            case WITHDRAW_COMMAND:
                doWithdraw();
                break;
            case TRANSFER_COMMAND:
                doTransfer();
                break;
            case TRANSACTIONS_COMMAND:
                doTransactionHistory();
                break;
            default:
                System.out.println("Command is not valid. Please try again.");
                break;
        }
    }

    // EFFECTS: prints current bank balance
    private void doCheckBalance() {
        Account userAccount = databaseInfo.get(username);
        System.out.println("Your current balance is: " + userAccount.getBalanceString() + "\n");
    }

    // EFFECTS: deposits money to bank account and prints confirmation
    private void doDeposit() {
        Account userAccount = databaseInfo.get(username);
        System.out.println("Deposit amount: $");
        String amount = input.nextLine();
        if (amount.matches("\\d+(\\.\\d+)?")) {
            System.out.println(userAccount.deposit(amount) + "\n");
        } else {
            System.out.println("Invalid amount. Please try again.");
            doDeposit();
        }
    }

    // EFFECTS: withdraws money from bank account and prints confirmation
    private void doWithdraw() {
        Account userAccount = databaseInfo.get(username);
        System.out.println("Withdraw amount: $");
        String amount = input.nextLine();
        if (amount.matches("\\d+(\\.\\d+)?")) {
            BigDecimal withdraw = new BigDecimal(amount);
            if (lessThanOrEqual(withdraw, userAccount.getBalance())) {
                System.out.println(userAccount.withdraw(amount) + "\n");
            } else {
                System.out.println("You have insufficient funds. \n");
            }
        } else {
            System.out.println("Invalid amount. Please try again.");
            doWithdraw();
        }
    }

    // EFFECTS: initiates transfer sequence and checks whether user receiving money is an existing user
    private void doTransfer() {
        System.out.println("Recipient username:");
        String recipientUser = input.nextLine();
        if (database.authUsername(recipientUser)) {
            doTransferActual(recipientUser);
        } else {
            System.out.println("User not found. Returning to menu. \n");
        }
    }

    // EFFECTS: continues transfer sequence and transfers money if sender has funds
    private void doTransferActual(String recipient) {
        Account userAccount = databaseInfo.get(username);
        Account recipientAccount = databaseInfo.get(recipient);
        System.out.println("Transfer amount: $");
        String amount = input.nextLine();
        if (amount.matches("\\d+(\\.\\d+)?")) {
            BigDecimal transfer = new BigDecimal(amount);
            if (lessThanOrEqual(transfer, userAccount.getBalance())) {
                doTransferFromTo(amount, userAccount, recipientAccount);
                System.out.println(recipientAccount.getName() + " has received your Interac eTransfer! \n");
            } else {
                System.out.println("You have insufficient funds. \n");
            }
        } else {
            System.out.println("Invalid amount. Please try again. \n");
            doTransferActual(recipient);
        }
    }

    // EFFECTS: displays complete history of transactions for user
    private void doTransactionHistory() {
        Account userAccount = databaseInfo.get(username);
        System.out.println(userAccount.transactionHistory());
    }

}

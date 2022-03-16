package ui;

import exceptions.*;
import model.Account;
import model.UserDatabase;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Scanner;

import static model.Formatting.*;
import static model.Security.*;

// Represents the Banking Application
public class BankingApp extends Options {
    private static final String JSON_STORE = "./data/database.json";
    private final JsonWriter jsonWriter;
    private final JsonReader jsonReader;
    private Scanner input;

    private static UserDatabase database;
    private static HashMap<String, Account> databaseInfo;

    static String username;
    static String password;
    static String newUsername;
    static String newPassword;
    static String newPasswordConfirm;
    static String newName;

    // EFFECTS: initiates Banking Application
    public BankingApp() {
        jsonReader = new JsonReader(JSON_STORE);

        // try to load database from save when opening,
        // otherwise create new database with 2 demo accounts (foo, bar)
        try {
            loadUserDatabase();
            System.out.println("File loaded");
        } catch (IOException ioe) {
            database = new UserDatabase(true);
            System.out.println("No file found, creating new database");
        }

        databaseInfo = database.getUserDatabase();
        mainMenu();

        // save database when closing
        jsonWriter = new JsonWriter(JSON_STORE);
        saveUserDatabase();
    }

    // EFFECTS: main menu processes user input
    private void mainMenu() {
        boolean runApp = true;
        String command;
        input = new Scanner(System.in);

        while (runApp) {
            displayMainMenuOptions();
            command = pretty(input.nextLine());
            if (command.equals(QUIT_COMMAND)) {
                runApp = false;
            } else {
                try {
                    parseCommand(command);
                } catch (InvalidCommandException ice) {
                    System.out.println(ice.getMessage());
                }
            }
        }
        System.out.println("Goodbye!");
    }

    // EFFECTS: parses commands from user on main menu
    private void parseCommand(String command) throws InvalidCommandException {
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
                throw new InvalidCommandException();
        }
    }

    // EFFECTS: initiates login sequence and verifies entered username
    private void doLogin() {
        System.out.println("Please enter your username.");
        username = input.nextLine();
        if (username.equals(QUIT_COMMAND)) {
            returnMain();
        } else {
            try {
                database.authUsername(username);
                System.out.println("Username exists.");
                doLoginPassword();
            } catch (UsernameNotFoundException unfe) {
                System.out.println(unfe.getMessage());
                doLogin();
            }
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
            } else {
                try {
                    database.authPassword(username, password, remainingTries);
                    System.out.println(databaseInfo.get(username).loginNotifications());
                    accountMenu();
                } catch (IncorrectPasswordTriesLeftException iptle) {
                    remainingTries--;
                    System.out.println(iptle.getMessage());
                } catch (IncorrectPasswordNoTriesLeftException ipntle) {
                    remainingTries = 0;
                    System.out.println(ipntle.getMessage());
                    countdownTimer();
                }
            }
        }
    }

    // EFFECTS: initiates registration sequence and allows users to enter a name
    private void doRegister() {
        System.out.println("Please enter your preferred name.");
        newName = input.nextLine();
        if (newName.equals(QUIT_COMMAND)) {
            returnMain();
        } else {
            try {
                isValidName(newName);
                registerUsername();
            } catch (InvalidNameException ine) {
                System.out.println(ine.getMessage());
                doRegister();
            }
        }
    }

    // EFFECTS: continues registration sequence and allows users to enter a username
    private void registerUsername() {
        System.out.println("Please enter your desired username.\nNote: cannot use '" + QUIT_COMMAND + "'.");
        newUsername = input.nextLine();
        if (newUsername.equals(QUIT_COMMAND)) {
            returnMain();
        } else {
            try {
                isValidEntry(newUsername);
                database.isUsernameFree(newUsername);
                registerPassword();
            } catch (IllegalEntryException | UsernameNotFreeException e) {
                System.out.println(e.getMessage());
                registerUsername();
            }
        }
    }

    // EFFECTS: continues registration sequence and allows users to enter a password
    private void registerPassword() {
        System.out.println("Please enter your desired password.");
        newPassword = input.nextLine();
        try {
            isValidEntry(newPassword);
            System.out.println("Please confirm your password.");
            newPasswordConfirm = input.nextLine();
            doPasswordsMatch(newPassword, newPasswordConfirm);
            database.storeAccount(newUsername, new Account(createPassword(newPassword), capitalizeName(newName)));
            System.out.println("Account has been registered. Thank you!\n");
        } catch (IllegalEntryException | PasswordsDoNotMatchException e) {
            System.out.println(e.getMessage());
            registerPassword();
        }
    }

    // EFFECTS: creates a new hashed password from a clear text password
    private String createPassword(String newPassword) {
        String salt = salt();
        return salt + hashFunction(salt + newPassword);
    }

    // EFFECTS: prints all usernames and passwords on database
    private void doHack() {
        System.out.println("*playing Mission Impossible theme* \nHacking the mainframe...");
        countdownTimer();
        System.out.println("AND WE'RE IN! \n");
        databaseInfo.forEach((k, v)
                -> System.out.println("Username: " + k + "  "
                + "Password: " + v.getPassword()));
        System.out.println("\n");
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
                try {
                    parseAccountCommand(accountCommand);
                } catch (InvalidCommandException ice) {
                    System.out.println(ice.getMessage());
                }
            }
        }
        System.out.println("You have been logged out. Press quit to return to main menu.");
    }

    // EFFECTS: parses commands once logged in
    private void parseAccountCommand(String command) throws InvalidCommandException {
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
                throw new InvalidCommandException();
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
        try {
            isValidAmount(amount);
            System.out.println(userAccount.deposit(amount) + "\n");
        } catch (InvalidAmountException iae) {
            System.out.println(iae.getMessage());
            doDeposit();
        }
    }

    // EFFECTS: withdraws money from bank account and prints confirmation
    private void doWithdraw() {
        Account userAccount = databaseInfo.get(username);
        System.out.println("Withdraw amount: $");
        String amount = input.nextLine();
        try {
            isValidAmount(amount);
            hasSufficientFunds(new BigDecimal(amount), userAccount.getBalance());
            System.out.println(userAccount.withdraw(amount) + "\n");
        } catch (InvalidAmountException iae) {
            System.out.println(iae.getMessage());
            doWithdraw();
        } catch (InsufficientFundsException ife) {
            System.out.println(ife.getMessage());
        }
    }

    // EFFECTS: initiates transfer sequence and checks whether user receiving money is an existing user
    private void doTransfer() {
        System.out.println("Recipient username:");
        String recipientUser = input.nextLine();
        try {
            database.authUsername(recipientUser);
            doTransferActual(recipientUser);
        } catch (UsernameNotFoundException unfe) {
            System.out.println(unfe.getMessage());
        }
    }

    // EFFECTS: continues transfer sequence and transfers money if sender has funds
    private void doTransferActual(String recipient) {
        Account senderAccount = databaseInfo.get(username);
        Account recipientAccount = databaseInfo.get(recipient);
        System.out.println("Transfer amount: $");
        String amount = input.nextLine();
        try {
            isValidAmount(amount);
            hasSufficientFunds(new BigDecimal(amount), senderAccount.getBalance());
            doTransferFromTo(amount, senderAccount, recipientAccount);
            System.out.println(recipientAccount.getName() + " has received your Interac eTransfer!\n");
        } catch (InvalidAmountException iae) {
            System.out.println(iae.getMessage());
            doTransferActual(recipient);
        } catch (InsufficientFundsException ife) {
            System.out.println(ife.getMessage());
        }
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: From sender:
    //          subtracts amount from sender balance,
    //          adds eTransfer to transaction history,
    //          To recipient:
    //          adds amount to recipient balance,
    //          adds eTransfer to transaction history,
    //          adds eTransfer to notifications list
    private void doTransferFromTo(String amount, Account sender, Account recipient) {
        sender.transferOut(amount, recipient.getName());
        recipient.transferIn(amount, sender.getName());
    }

    // EFFECTS: displays complete history of transactions for user
    private void doTransactionHistory() {
        Account userAccount = databaseInfo.get(username);
        System.out.println(userAccount.transactionHistory());
    }

    // EFFECTS: saves user database to file
    private void saveUserDatabase() {
        try {
            jsonWriter.open();
            jsonWriter.write(database);
            jsonWriter.close();
            System.out.println("Saved user database to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads user database from file
    private void loadUserDatabase() throws IOException {
        database = jsonReader.read();
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: A 5-second countdown is initiated, printing each second
    public static void countdownTimer() {
        int time = 5;
        while (time > 0) {
            try {
                Thread.sleep(5);
            } catch (Exception e) {
                System.out.println("Exception caught in countdownTimer.");
                break;
            }
            System.out.println(time + "...");
            time--;
        }
    }
}

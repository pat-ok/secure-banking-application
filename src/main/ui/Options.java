package ui;

// Represents an abstract class with options display, formatting, and big decimal comparator methods
public abstract class Options {

    // main menu commands
    protected static final String LOGIN_COMMAND = "login";
    protected static final String REGISTER_COMMAND = "register";
    protected static final String HACK_COMMAND = "hack";
    protected static final String QUIT_COMMAND = "quit";

    // account menu commands
    protected static final String CHECK_BALANCE_COMMAND = "balance";
    protected static final String DEPOSIT_COMMAND = "deposit";
    protected static final String WITHDRAW_COMMAND = "withdraw";
    protected static final String TRANSFER_COMMAND = "transfer";
    protected static final String TRANSACTIONS_COMMAND = "transactions";
    protected static final String LOGOUT_COMMAND = "logout";

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: displays all main menu options
    protected void displayMainOptions() {
        System.out.println("Welcome to BigDecimal Bank of Canada. How can we help you today?");
        System.out.println("Select from:  ["
                + LOGIN_COMMAND + " | "
                + REGISTER_COMMAND + " | "
                + HACK_COMMAND + " | "
                + QUIT_COMMAND + "]");
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: displays all account options once logged in
    protected void displayAccountOptions() {
        System.out.println("Select from:  ["
                + CHECK_BALANCE_COMMAND + " | "
                + DEPOSIT_COMMAND + " | "
                + WITHDRAW_COMMAND + " | "
                + TRANSFER_COMMAND + " | "
                + TRANSACTIONS_COMMAND + " | "
                + LOGOUT_COMMAND + "]");
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: prints returning to main menu message
    protected static void returnMain() {
        System.out.println("Returning to main menu.\n");
    }
}

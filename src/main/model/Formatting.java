package model;

import exceptions.*;

import java.math.BigDecimal;
import java.text.NumberFormat;

// Represents a public class with static input checking and formatting methods
// Should be abstract with no constructors if it weren't for jUnit?
public class Formatting {
    private final String junit;

    // FOR JUNIT
    public Formatting() {
        this.junit = "for junit";
    }

    // FOR JUNIT
    public String getJunit() {
        return junit;
    }

    // REQUIRES: nothing
    // MODIFIES: s
    // EFFECTS: removes numbers, spaces, and capitalization from inputted command
    public static String pretty(String s) {
        s = s.toLowerCase();
        s = s.replaceAll("\\d", "");
        s = s.replaceAll(" ", "");
        return s;
    }

    // REQUIRES: s does not have illegal traits:
    //           empty,
    //           only whitespace,
    //           leading or trailing whitespaces
    // MODIFIES: nothing
    // EFFECTS: nothing
    public static void isValidEntry(String s) throws IllegalEntryException {
        if (s.matches(".{0}|( )*|( )+.*( )*|( )*.*( )+")) {
            throw new IllegalEntryException();
        }
    }

    // REQUIRES: name does not have illegal traits:
    //           empty,
    //           only whitespace,
    //           has non-alphabetical characters
    // MODIFIES: nothing
    // EFFECTS: nothing
    public static void isValidName(String name) throws InvalidNameException {
        if (name.matches(".{0}|( )*") || !name.matches("(( )*[A-Za-z]*( )*)*")) {
            throw new InvalidNameException();
        }
    }

    // REQUIRES: amount must be validly monetary
    // MODIFIES: nothing
    // EFFECTS: nothing
    public static void isValidAmount(String amount) throws InvalidAmountException {
        if (!amount.matches("\\d+(\\.\\d+)?")) {
            throw new InvalidAmountException();
        }
    }

    // REQUIRES: nothing
    // MODIFIES: name
    // EFFECTS: Capitalizes first character of all strings separated by whitespace
    public static String capitalizeName(String name) {
        char[] nameChars = name.toLowerCase().toCharArray();
        boolean spaceBefore = true;
        for (int i = 0; i < nameChars.length; i++) {
            if (spaceBefore && !Character.isWhitespace(nameChars[i])) {
                nameChars[i] = Character.toUpperCase(nameChars[i]);
                spaceBefore = false;
            } else if (Character.isWhitespace(nameChars[i])) {
                spaceBefore = true;
            }
        }
        return String.valueOf(nameChars).replaceAll("( ){2,}", " ").trim();
    }

    // REQUIRES: password matches confirmation password
    // MODIFIES: nothing
    // EFFECTS: nothing
    public static void doPasswordsMatch(String password, String passwordConfirm) throws PasswordsDoNotMatchException {
        if (!password.equals(passwordConfirm)) {
            throw new PasswordsDoNotMatchException();
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
    // TODO : put under bankingapp
    public static void doTransferFromTo(String amount, Account sender, Account recipient) {
        sender.transferOut(amount, recipient.getName());
        recipient.transferIn(amount, sender.getName());
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: returns a BigDecimal number into currency format as a string
    public static String currencyFormat(BigDecimal bd) {
        return NumberFormat.getCurrencyInstance().format(bd);
    }

    // REQUIRES: outgoing <= balance
    // MODIFIES: nothing
    // EFFECTS: nothing
    public static void hasSufficientFunds(BigDecimal outgoing, BigDecimal balance) throws InsufficientFundsException {
        if (!(outgoing.compareTo(balance) <= 0)) {
            throw new InsufficientFundsException();
        }
    }
}

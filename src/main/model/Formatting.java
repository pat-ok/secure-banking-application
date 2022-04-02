package model;

import exceptions.amount.AmountFailedInsufficientFundsException;
import exceptions.amount.AmountFailedInvalidEntryException;
import exceptions.registration.RegistrationFailedInvalidEntryException;
import exceptions.registration.RegistrationFailedInvalidNameException;
import exceptions.registration.RegistrationFailedNameTooLongException;
import exceptions.registration.RegistrationFailedPasswordsDoNotMatchException;

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
    public static void isValidEntry(String s) throws RegistrationFailedInvalidEntryException {
        if (s.matches(".{0}|( )*|( )+.*( )*|( )*.*( )+")) {
            throw new RegistrationFailedInvalidEntryException();
        }
    }

    // REQUIRES: name does not have illegal traits:
    //           empty,
    //           only whitespace,
    //           has non-alphabetical characters
    //           over 20 characters long (not including whitespace)
    // MODIFIES: nothing
    // EFFECTS: nothing
    public static void isValidName(String name)
            throws RegistrationFailedInvalidNameException, RegistrationFailedNameTooLongException {
        if (name.matches(".{0}|( )*") || !name.matches("(( )*[A-Za-z]*( )*)*")) {
            throw new RegistrationFailedInvalidNameException();
        } else if (name.replaceAll(" ", "").length() > 20) {
            throw new RegistrationFailedNameTooLongException();
        }
    }

    // REQUIRES: amount must be validly monetary
    // MODIFIES: nothing
    // EFFECTS: nothing
    public static void isValidAmount(String amount) throws AmountFailedInvalidEntryException {
        if (!amount.matches("\\d+(\\.\\d+)?")) {
            throw new AmountFailedInvalidEntryException();
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
    public static void doPasswordsMatch(String password, String passwordConfirm)
            throws RegistrationFailedPasswordsDoNotMatchException {
        if (!password.equals(passwordConfirm)) {
            throw new RegistrationFailedPasswordsDoNotMatchException();
        }
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
    public static void hasSufficientFunds(BigDecimal outgoing, BigDecimal balance)
            throws AmountFailedInsufficientFundsException {
        if (!(outgoing.compareTo(balance) <= 0)) {
            throw new AmountFailedInsufficientFundsException();
        }
    }
}

package model;

import java.math.BigDecimal;
import java.text.NumberFormat;

public class Formatting {
    private String junit;

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

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: returns true if entry has illegal traits:
    //          empty,
    //          only whitespace,
    //          leading or trailing whitespaces
    public static boolean isInvalidEntry(String s) {
        return s.matches(".{0}|( )*|( )+.*( )*|( )*.*( )+");
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: returns true if entry for name has illegal traits:
    //          empty,
    //          only whitespace,
    //          leading or trailing whitespaces,
    //          has non-alphabetical characters
    public static boolean isInvalidName(String name) {
        return isInvalidEntry(name) || !name.matches("(( )*[A-Za-z]*( )*)*");
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
        return String.valueOf(nameChars).replaceAll("( ){2,}", " ");
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

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: BigDecimal comparator returning true if x >= y
    public static boolean lessThanOrEqual(BigDecimal x, BigDecimal y) {
        return (x.compareTo(y) <= 0);
    }
}

package exceptions;

public class IllegalEntryException extends Exception {
    public IllegalEntryException() {
        super("Field contains illegal characters. Please try again.\n");
    }
}

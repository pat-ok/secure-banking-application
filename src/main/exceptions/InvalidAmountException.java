package exceptions;

public class InvalidAmountException extends Exception {
    public InvalidAmountException() {
        super("Invalid amount. Please try again.");
    }
}

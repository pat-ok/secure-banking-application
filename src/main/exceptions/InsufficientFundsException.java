package exceptions;

public class InsufficientFundsException extends Exception {
    public InsufficientFundsException() {
        super("You have insufficient funds. Sorry!\n");
    }
}

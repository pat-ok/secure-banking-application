package exceptions;

public class InvalidCommandException extends Exception {
    public InvalidCommandException() {
        super("Invalid command. Please try again.\n");
    }
}

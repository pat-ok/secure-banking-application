package exceptions;

public class InvalidNameException extends Exception {
    public InvalidNameException() {
        super("Name is invalid. Please try again.\n");
    }
}

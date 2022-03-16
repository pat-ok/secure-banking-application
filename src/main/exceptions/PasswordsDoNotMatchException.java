package exceptions;

public class PasswordsDoNotMatchException extends Exception {
    public PasswordsDoNotMatchException() {
        super("Passwords do not match. Please try again.\n");
    }
}

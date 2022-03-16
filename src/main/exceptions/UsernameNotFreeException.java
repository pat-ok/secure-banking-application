package exceptions;

public class UsernameNotFreeException extends Exception {
    public UsernameNotFreeException() {
        super("Username is already taken. Please try again.\n");
    }
}

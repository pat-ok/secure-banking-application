package exceptions;

public class UsernameNotFoundException extends Exception {
    public UsernameNotFoundException() {
        super("Username does not exist. Please try again.");
    }
}

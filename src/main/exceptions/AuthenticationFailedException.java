package exceptions;

public class AuthenticationFailedException extends Exception {
    public AuthenticationFailedException(String msg) {
        super("Username or password is incorrect!");
    }
}

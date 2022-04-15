package exceptions.authentication;

public class AuthenticationFailedException extends Exception {
    public AuthenticationFailedException(String msg) {
        super(msg);
    }
}

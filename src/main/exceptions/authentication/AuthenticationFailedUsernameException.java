package exceptions.authentication;

public class AuthenticationFailedUsernameException extends AuthenticationFailedException {
    public AuthenticationFailedUsernameException() {
        super("Username not found!");
    }
}

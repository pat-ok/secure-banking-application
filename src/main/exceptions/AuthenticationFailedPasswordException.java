package exceptions;

public class AuthenticationFailedPasswordException extends AuthenticationFailedException {
    public AuthenticationFailedPasswordException() {
        super("Password does not match username!");
    }
}

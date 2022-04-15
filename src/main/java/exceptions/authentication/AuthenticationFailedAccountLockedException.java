package exceptions.authentication;

public class AuthenticationFailedAccountLockedException extends AuthenticationFailedException {
    public AuthenticationFailedAccountLockedException() {
        super("Account is locked!");
    }
}

package exceptions;

public class AuthenticationFailedAccountLockedException extends AuthenticationFailedException {
    public AuthenticationFailedAccountLockedException() {
        super("Account is locked!");
    }
}

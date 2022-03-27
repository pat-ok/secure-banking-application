package exceptions;

public class RegistrationFailedPasswordsDoNotMatchException extends RegistrationFailedException {
    public RegistrationFailedPasswordsDoNotMatchException() {
        super("Passwords do not match!");
    }
}

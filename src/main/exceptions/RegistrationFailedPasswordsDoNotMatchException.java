package exceptions;

public class RegistrationFailedPasswordsDoNotMatchException extends RegistrationFailedException {
    public RegistrationFailedPasswordsDoNotMatchException() {
        super("Does not match!");
    }
}

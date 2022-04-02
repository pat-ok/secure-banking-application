package exceptions.registration;

public class RegistrationFailedPasswordsDoNotMatchException extends RegistrationFailedException {
    public RegistrationFailedPasswordsDoNotMatchException() {
        super("Does not match!");
    }
}

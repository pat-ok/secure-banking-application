package exceptions;

public class RegistrationFailedUsernameNotFreeException extends RegistrationFailedException {
    public RegistrationFailedUsernameNotFreeException() {
        super("Username is not available!");
    }
}

package exceptions;

public class RegistrationFailedUsernameNotFreeException extends RegistrationFailedException {
    public RegistrationFailedUsernameNotFreeException() {
        super("Username not available!");
    }
}

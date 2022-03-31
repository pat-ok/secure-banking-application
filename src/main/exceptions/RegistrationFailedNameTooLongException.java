package exceptions;

public class RegistrationFailedNameTooLongException extends RegistrationFailedException {
    public RegistrationFailedNameTooLongException() {
        super("Name too long!");
    }
}

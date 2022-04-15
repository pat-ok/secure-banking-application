package exceptions.registration;

public class RegistrationFailedNameTooLongException extends RegistrationFailedException {
    public RegistrationFailedNameTooLongException() {
        super("Name too long!");
    }
}

package exceptions;

public class RegistrationFailedInvalidNameException extends RegistrationFailedException {
    public RegistrationFailedInvalidNameException() {
        super("Name is invalid!");
    }
}

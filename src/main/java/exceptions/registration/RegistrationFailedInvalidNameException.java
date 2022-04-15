package exceptions.registration;

public class RegistrationFailedInvalidNameException extends RegistrationFailedException {
    public RegistrationFailedInvalidNameException() {
        super("Invalid name!");
    }
}

package exceptions.registration;

public class RegistrationFailedInvalidEntryException extends RegistrationFailedException {
    public RegistrationFailedInvalidEntryException() {
        super("Invalid characters!");
    }
}

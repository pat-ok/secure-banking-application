package exceptions;

public class RegistrationFailedInvalidEntryException extends RegistrationFailedException {
    public RegistrationFailedInvalidEntryException() {
        super("Invalid characters!");
    }
}

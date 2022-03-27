package exceptions;

public class RegistrationFailedInvalidEntryException extends RegistrationFailedException {
    public RegistrationFailedInvalidEntryException() {
        super("Field contains invalid characters!");
    }
}

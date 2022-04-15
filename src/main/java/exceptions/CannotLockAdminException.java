package exceptions;

public class CannotLockAdminException extends Exception {
    public CannotLockAdminException() {
        super("Cannot lock admin!");
    }
}

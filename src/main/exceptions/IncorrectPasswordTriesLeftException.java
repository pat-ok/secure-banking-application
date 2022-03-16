package exceptions;

public class IncorrectPasswordTriesLeftException extends IncorrectPasswordException {
    public IncorrectPasswordTriesLeftException(int tries) {
        super("Password is incorrect. You have " + (tries - 1) + " attempt(s) left.");
    }

}

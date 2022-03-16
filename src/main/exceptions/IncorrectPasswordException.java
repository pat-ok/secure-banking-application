package exceptions;

public class IncorrectPasswordException extends Exception {
    public IncorrectPasswordException(String msg) {
        super(msg);
    }
}

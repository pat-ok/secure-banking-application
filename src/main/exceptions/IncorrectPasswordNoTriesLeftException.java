package exceptions;

public class IncorrectPasswordNoTriesLeftException extends IncorrectPasswordException {
    public IncorrectPasswordNoTriesLeftException() {
        super("Login failed. Account locked for 5 seconds. \nYou are being surrounded by police!");
    }
}

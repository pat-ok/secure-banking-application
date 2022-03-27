package exceptions;

public class UnconfirmedException extends Exception {
    public UnconfirmedException() {
        super("Please confirm!");
    }
}

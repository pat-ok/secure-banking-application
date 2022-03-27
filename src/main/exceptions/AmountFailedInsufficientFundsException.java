package exceptions;

public class AmountFailedInsufficientFundsException extends AmountFailedException {
    public AmountFailedInsufficientFundsException() {
        super("Insufficient funds!");
    }
}

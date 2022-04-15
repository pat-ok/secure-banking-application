package exceptions.amount;

public class AmountFailedInsufficientFundsException extends AmountFailedException {
    public AmountFailedInsufficientFundsException() {
        super("Insufficient funds!");
    }
}

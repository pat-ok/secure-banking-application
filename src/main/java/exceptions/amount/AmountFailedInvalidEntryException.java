package exceptions.amount;

public class AmountFailedInvalidEntryException extends AmountFailedException {
    public AmountFailedInvalidEntryException() {
        super("Invalid amount!");
    }
}

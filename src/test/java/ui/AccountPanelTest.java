package ui;

import exceptions.UnconfirmedException;
import org.junit.Test;

import static org.junit.Assert.fail;

public class AccountPanelTest {

    @Test
    public void testConfirmationTrueThrows() {
        try {
            confirmationTrue("false");
            fail("Text is not 'confirm'");
        } catch (UnconfirmedException ex) {
            // pass
        }
    }

    @Test
    public void testConfirmationTrueDoesNotThrow() {
        try {
            confirmationTrue("confirm");
            // pass
        } catch (UnconfirmedException ex) {
            fail("Should be confirmed");
        }
    }

    // EFFECTS: checks whether confirmation string is "confirm"
    private void confirmationTrue(String confirmation) throws UnconfirmedException {
        if (!confirmation.equals("confirm")) {
            throw new UnconfirmedException();
        }
    }
}

package ui;

import exceptions.UnconfirmedException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

public class AccountPanelTest {

    @Test
    void testConfirmationTrueThrows() {
        try {
            confirmationTrue("false");
            fail("Text is not 'confirm'");
        } catch (UnconfirmedException ex) {
            // pass
        }
    }

    @Test
    void testConfirmationTrueDoesNotThrow() {
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

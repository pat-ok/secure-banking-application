package ui;

import exceptions.InvalidCommandException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

public class InvalidCommandExceptionTest {

    private static final String invalidCommand = "invalid";

    @Test
    void testInvalidCommandExceptionThrown() {
        try {
            testParseCommand(invalidCommand);
            fail("Exception should be thrown");
        } catch (InvalidCommandException ice) {
            // pass
        }
    }

    @Test
    void testInvalidCommandExceptionNotThrown() {
        try {
            testParseCommand("valid");
            // pass
        } catch (InvalidCommandException ice) {
            fail("Exception should not be thrown");
        }
    }

    // REQUIRES: command must not be invalid
    // MODIFIES: nothing
    // EFFECTS: nothing
    private void testParseCommand(String command) throws InvalidCommandException {
        if (command.equals(invalidCommand)) {
            throw new InvalidCommandException();
        }
    }
}

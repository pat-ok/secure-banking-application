package ui;

import exceptions.registration.RegistrationFailedMatchesHintException;
import exceptions.registration.RegistrationFailedMatchesHintPasswordConfirmException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

public class RegisterPanelTest {

    private String newNameHint = "Name";
    private String newPasswordConfirmHint = "Confirm password";

    @Test
    void testMatchesHintNameThrows() {
        try {
            matchesHintName("Name");
            fail("Does match prompt");
        } catch (RegistrationFailedMatchesHintException ex) {
            // pass
        }
    }

    @Test
    void testMatchesHintNameDoesNotThrow() {
        try {
            matchesHintName("Nope");
            // pass
        } catch (RegistrationFailedMatchesHintException ex) {
            fail("Should not match name");
        }
    }

    @Test
    void testMatchesHintPasswordConfirmThrows() {
        try {
            matchesHintPasswordConfirm("Confirm password");
            fail("Does match prompt");
        } catch (RegistrationFailedMatchesHintPasswordConfirmException ex) {
            // pass
        }
    }

    @Test
    void testMatchesHintPasswordConfirmDoesNotThrow() {
        try {
            matchesHintPasswordConfirm("Nope");
            // pass
        } catch (RegistrationFailedMatchesHintPasswordConfirmException ex) {
            fail("Should not match name");
        }
    }

    // EFFECTS: Checks if string matches name hint
    private void matchesHintName(String name) throws RegistrationFailedMatchesHintException {
        if (name.equals(newNameHint)) {
            throw new RegistrationFailedMatchesHintException();
        }
    }

    // EFFECTS: Checks if string matches password confirmation hint
    private void matchesHintPasswordConfirm(String name) throws RegistrationFailedMatchesHintPasswordConfirmException {
        if (name.equals(newPasswordConfirmHint)) {
            throw new RegistrationFailedMatchesHintPasswordConfirmException();
        }
    }
}

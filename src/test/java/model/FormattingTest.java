package model;

import exceptions.amount.AmountFailedInsufficientFundsException;
import exceptions.amount.AmountFailedInvalidEntryException;
import exceptions.registration.RegistrationFailedInvalidEntryException;
import exceptions.registration.RegistrationFailedInvalidNameException;
import exceptions.registration.RegistrationFailedNameTooLongException;
import exceptions.registration.RegistrationFailedPasswordsDoNotMatchException;
import org.junit.Test;

import java.math.BigDecimal;

import static model.Formatting.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class FormattingTest {

    @Test
    public void testFormatting() {
        Formatting testFormatting = new Formatting();
        assertEquals("for junit", testFormatting.getJunit());

    }

    @Test
    public void testPretty() {
        assertEquals("", pretty(""));
        assertEquals("testing", pretty("TeStInG"));
        assertEquals("testing", pretty("TeStInG123"));
        assertEquals("testing", pretty("  1 2 T e S t I n G 1 2  "));
    }

    @Test
    public void testIsValidEntryEmpty() {
        try {
            isValidEntry("");
            fail("String is illegal");
        } catch (RegistrationFailedInvalidEntryException ex) {
            // pass
        }
    }

    @Test
    public void testIsValidEntryOnlySpace() {
        try {
            isValidEntry(" ");
            fail("String is illegal");
        } catch (RegistrationFailedInvalidEntryException ex) {
            // pass
        }
    }

    @Test
    public void testIsValidEntryLeadingSpace() {
        try {
            isValidEntry(" test");
            fail("String is illegal");
        } catch (RegistrationFailedInvalidEntryException ex) {
            // pass
        }
    }

    @Test
    public void testIsValidEntryTrailingSpace() {
        try {
            isValidEntry("test ");
            fail("String is illegal");
        } catch (RegistrationFailedInvalidEntryException ex) {
            // pass
        }
    }

    @Test
    public void testIsValidEntryValid() {
        try {
            isValidEntry("test");
            // pass
        } catch (RegistrationFailedInvalidEntryException ex) {
            fail("String is legal");
        }
    }

    @Test
    public void testIsValidNameNumbers() {
        try {
            isValidName("123");
            fail("Name is illegal");
        } catch (RegistrationFailedNameTooLongException ex) {
            fail("Name is not too long");
        } catch (RegistrationFailedInvalidNameException ex) {
            // pass
        }
    }

    @Test
    public void testIsValidNameEmpty() {
        try {
            isValidName("");
            fail("Name is illegal");
        } catch (RegistrationFailedNameTooLongException ex) {
            fail("Name is not too long");
        } catch (RegistrationFailedInvalidNameException ex) {
            // pass
        }
    }

    @Test
    public void testIsValidNameOnlySpace() {
        try {
            isValidName(" ");
            fail("Name is illegal");
        } catch (RegistrationFailedNameTooLongException ex) {
            fail("Name is not too long");
        } catch (RegistrationFailedInvalidNameException ex) {
            // pass
        }
    }

    @Test
    public void testIsValidNameNumbersAndLetters() {
        try {
            isValidName("123test");
            fail("Name is illegal");
        } catch (RegistrationFailedNameTooLongException ex) {
            fail("Name is not too long");
        } catch (RegistrationFailedInvalidNameException ex) {
            // pass
        }
    }

    @Test
    public void testIsValidNameWord() {
        try {
            isValidName("test");
            // pass
        } catch (RegistrationFailedNameTooLongException ex) {
            fail("Name is not too long");
        } catch (RegistrationFailedInvalidNameException ex) {
            fail("Name is legal");
        }
    }

    @Test
    public void testIsValidNameWordLeadingSpace() {
        try {
            isValidName(" test");
            // pass
        } catch (RegistrationFailedNameTooLongException ex) {
            fail("Name is not too long");
        } catch (RegistrationFailedInvalidNameException ex) {
            fail("Name is legal");
        }
    }

    @Test
    public void testIsValidNameWordTrailingSpace() {
        try {
            isValidName("test ");
            // pass
        } catch (RegistrationFailedNameTooLongException ex) {
            fail("Name is not too long");
        } catch (RegistrationFailedInvalidNameException ex) {
            fail("Name is legal");
        }
    }

    @Test
    public void testIsValidNameTwoWords() {
        try {
            isValidName("first last");
            // pass
        } catch (RegistrationFailedNameTooLongException ex) {
            fail("Name is not too long");
        } catch (RegistrationFailedInvalidNameException ex) {
            fail("Name is legal");
        }
    }

    @Test
    public void testIsValidNameThreeWords() {
        try {
            isValidName("first middle last");
            // pass
        } catch (RegistrationFailedNameTooLongException ex) {
            fail("Name is not too long");
        } catch (RegistrationFailedInvalidNameException ex) {
            fail("Name is legal");
        }
    }

    @Test
    public void testIsValidNameExtraSpacesWords() {
        try {
            isValidName("first    middle    last");
            // pass
        } catch (RegistrationFailedNameTooLongException ex) {
            fail("Name is not too long");
        } catch (RegistrationFailedInvalidNameException ex) {
            fail("Name is legal");
        }
    }

    @Test
    public void testIsValidNameUnderBoundary() {
        try {
            isValidName("QWERTYUIOP QWERTYUIO");
        } catch (RegistrationFailedInvalidNameException ex) {
            fail("Characters are legal");
        } catch (RegistrationFailedNameTooLongException ex) {
            fail("Name is not too long");
        }
    }

    @Test
    public void testIsValidNameOnBoundary() {
        try {
            isValidName("QWERTYUIOP QWERTYUIOP");
        } catch (RegistrationFailedInvalidNameException ex) {
            fail("Characters are legal");
        } catch (RegistrationFailedNameTooLongException ex) {
            fail("Name is not too long");
        }
    }

    @Test
    public void testIsValidNameOverBoundary() {
        try {
            isValidName("QWERTYUIOP QWERTYUIOP Q");
            fail("Name is too long");
        } catch (RegistrationFailedInvalidNameException ex) {
            fail("Characters are legal");
        } catch (RegistrationFailedNameTooLongException ex) {
            // pass
        }
    }

    @Test
    public void testIsValidAmountLetters() {
        try {
            isValidAmount("not numbers");
            fail("Illegal amount");
        } catch (AmountFailedInvalidEntryException ex) {
            // pass
        }
    }

    @Test
    public void testIsValidAmountNegativeAmount() {
        try {
            isValidAmount("-50");
            fail("Illegal amount");
        } catch (AmountFailedInvalidEntryException ex) {
            // pass
        }
    }

    @Test
    public void testIsValidAmountTwoDecimals() {
        try {
            isValidAmount("10.20.30");
            fail("Illegal amount");
        } catch (AmountFailedInvalidEntryException ex) {
            // pass
        }
    }

    @Test
    public void testIsValidAmountProperNoDecimal() {
        try {
            isValidAmount("50");
            // pass
        } catch (AmountFailedInvalidEntryException ex) {
            fail("Amount is legal");
        }
    }

    @Test
    public void testIsValidAmountProperDecimal() {
        try {
            isValidAmount("50.10");
            // pass
        } catch (AmountFailedInvalidEntryException ex) {
            fail("Amount is legal");
        }
    }

    @Test
    public void testIsValidAmountProperDecimalMore() {
        try {
            isValidAmount("50.1020");
            // pass
        } catch (AmountFailedInvalidEntryException ex) {
            fail("Amount is legal");
        }
    }

    @Test
    public void testCapitalizeName() {
        assertEquals("Testing", capitalizeName("testing"));
        assertEquals("Testing Again", capitalizeName("testing again"));
        assertEquals("Testing Three Words", capitalizeName("testing three words"));
        assertEquals("Testing Spaces", capitalizeName("testing      spaces"));
        assertEquals("Testing More Spaces", capitalizeName("testing    mOre  spAces"));
        assertEquals("Testing Capitals", capitalizeName("tESTING cAPITALS"));
        assertEquals("First Middle Last", capitalizeName("   first   midDle lAST"));
    }

    @Test
    public void testDoPasswordsMatchMatching() {
        try {
            doPasswordsMatch("matching", "matching");
            // pass
        } catch (RegistrationFailedPasswordsDoNotMatchException ex) {
            fail("Password matches confirmation");
        }
    }

    @Test
    public void testDoPasswordsMatchNotMatching() {
        try {
            doPasswordsMatch("matching", "notmatching");
            fail("Password does not match confirmation");
        } catch (RegistrationFailedPasswordsDoNotMatchException ex) {
            // pass
        }
    }

    @Test
    public void testCurrencyFormat() {
        BigDecimal testAmount0 = new BigDecimal("0");
        BigDecimal testAmount1 = new BigDecimal("00.50");
        BigDecimal testAmount2 = new BigDecimal("50");
        BigDecimal testAmount3 = new BigDecimal("77.77");
        BigDecimal testAmount4 = new BigDecimal("100.00");

        assertEquals("0.00", currencyFormat(testAmount0).substring(1));
        assertEquals("0.50", currencyFormat(testAmount1).substring(1));
        assertEquals("50.00", currencyFormat(testAmount2).substring(1));
        assertEquals("77.77", currencyFormat(testAmount3).substring(1));
        assertEquals("100.00", currencyFormat(testAmount4).substring(1));
    }

    @Test
    public void testHasSufficientFundsEnough() {
        BigDecimal out = new BigDecimal("5");
        BigDecimal balance = new BigDecimal("10");

        try {
            hasSufficientFunds(out, balance);
            // pass
        } catch (AmountFailedInsufficientFundsException ex) {
            fail("There are enough funds");
        }
    }

    @Test
    public void testHasSufficientFundsEnoughBelowBoundary() {
        BigDecimal out = new BigDecimal("9.99");
        BigDecimal balance = new BigDecimal("10");

        try {
            hasSufficientFunds(out, balance);
            // pass
        } catch (AmountFailedInsufficientFundsException ex) {
            fail("There are enough funds");
        }
    }

    @Test
    public void testHasSufficientFundsEnoughBoundary() {
        BigDecimal out = new BigDecimal("10");
        BigDecimal balance = new BigDecimal("10");

        try {
            hasSufficientFunds(out, balance);
            // pass
        } catch (AmountFailedInsufficientFundsException ex) {
            fail("There are enough funds");
        }
    }

    @Test
    public void testHasSufficientFundsNotEnoughBoundary() {
        BigDecimal out = new BigDecimal("10.01");
        BigDecimal balance = new BigDecimal("10");

        try {
            hasSufficientFunds(out, balance);
            fail("There are not enough funds");
        } catch (AmountFailedInsufficientFundsException ex) {
            // pass
        }
    }

    @Test
    public void testHasSufficientFundsNotEnoughGreater() {
        BigDecimal out = new BigDecimal("15");
        BigDecimal balance = new BigDecimal("10");

        try {
            hasSufficientFunds(out, balance);
            fail("There are not enough funds");
        } catch (AmountFailedInsufficientFundsException ex) {
            // pass
        }
    }
}

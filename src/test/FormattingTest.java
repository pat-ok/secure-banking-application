import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static model.Formatting.*;
import static org.junit.jupiter.api.Assertions.*;

public class FormattingTest {

    @Test
    void testPretty() {
        assertEquals("", pretty(""));
        assertEquals("testing", pretty("TeStInG"));
        assertEquals("testing", pretty("TeStInG123"));
        assertEquals("testing", pretty("  1 2 T e S t I n G 1 2  "));
    }

    @Test
    void testInvalidEntry() {
        assertTrue(isInvalidEntry(""));
        assertTrue(isInvalidEntry(" "));
        assertTrue(isInvalidEntry("  "));
        assertTrue(isInvalidEntry(" test"));
        assertTrue(isInvalidEntry("test "));
        assertFalse(isInvalidEntry("test"));
    }

    @Test
    void testCapitalizeName() {
        assertEquals("Testing", capitalizeName("testing"));
        assertEquals("Testing Again", capitalizeName("testing again"));
        assertEquals("Testing Three Words", capitalizeName("testing three words"));
    }

    @Test
    void testCurrencyFormat() {
        BigDecimal testAmount0 = new BigDecimal("0");
        BigDecimal testAmount1 = new BigDecimal("00.50");
        BigDecimal testAmount2 = new BigDecimal("50");
        BigDecimal testAmount3 = new BigDecimal("77.77");
        BigDecimal testAmount4 = new BigDecimal("100.00");

        assertEquals("$0.00", currencyFormat(testAmount0));
        assertEquals("$0.50", currencyFormat(testAmount1));
        assertEquals("$50.00", currencyFormat(testAmount2));
        assertEquals("$77.77", currencyFormat(testAmount3));
        assertEquals("$100.00", currencyFormat(testAmount4));
    }

    @Test
    void testLessThanOrEqual() {
        // less
        BigDecimal x0 = new BigDecimal("5");
        BigDecimal y0 = new BigDecimal("10");

        assertTrue(lessThanOrEqual(x0, y0));

        // less boundary
        BigDecimal x1 = new BigDecimal("9.99");
        BigDecimal y1 = new BigDecimal("10");

        assertTrue(lessThanOrEqual(x1, y1));

        // equals
        BigDecimal x2 = new BigDecimal("10");
        BigDecimal y2 = new BigDecimal("10");

        assertTrue(lessThanOrEqual(x2, y2));

        // greater boundary
        BigDecimal x3 = new BigDecimal("10.01");
        BigDecimal y3 = new BigDecimal("10");

        assertFalse(lessThanOrEqual(x3, y3));

        // greater
        BigDecimal x4 = new BigDecimal("15");
        BigDecimal y4 = new BigDecimal("10");

        assertFalse(lessThanOrEqual(x4, y4));
    }
}

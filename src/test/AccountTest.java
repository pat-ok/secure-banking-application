import model.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountTest {
    private Account Foo;
    private Account Bar;

    @BeforeEach
    void runBefore() {
        Foo = new Account("pass123", "Foo");
        Bar = new Account("pass123", "Bar");
    }

    @Test
    void testConstructor() {
        BigDecimal testBalance = new BigDecimal("100");

        assertEquals("pass123", Foo.getPassword());
        assertEquals("Foo", Foo.getName());
        assertEquals(testBalance, Foo.getBalance());
        assertEquals(3, Foo.getNotifications().size());
        assertEquals(0, Foo.getTransactions().size());
    }

    @Test
    void testDepositOnce() {
        BigDecimal testBalance = new BigDecimal("150");
        Foo.deposit("50");

        assertEquals(testBalance, Foo.getBalance());
        assertEquals(1, Foo.getTransactions().size());
    }

    @Test
    void testDepositMany() {
        BigDecimal testBalance = new BigDecimal("250");
        Foo.deposit("25");
        Foo.deposit("50");
        Foo.deposit("75");

        assertEquals(testBalance, Foo.getBalance());
        assertEquals(3, Foo.getTransactions().size());
    }

    @Test
    void testWithdrawOnce() {
        BigDecimal testBalance = new BigDecimal("50");
        Foo.withdraw("50");

        assertEquals(testBalance, Foo.getBalance());
        assertEquals(1, Foo.getTransactions().size());
    }

    @Test
    void testWithdrawMany() {
        BigDecimal testBalance = new BigDecimal("25");
        Foo.withdraw("20");
        Foo.withdraw("25");
        Foo.withdraw("30");

        assertEquals(testBalance, Foo.getBalance());
        assertEquals(3, Foo.getTransactions().size());
    }

    @Test
    void testLoginNotificationsClear() {
        Foo.loginNotifications();

        assertEquals(0, Foo.getNotifications().size());
    }

    @Test
    void testTransferOutOnce() {
        BigDecimal testBalance = new BigDecimal("50");
        Foo.transferOut("50", Bar.getName());

        assertEquals(testBalance, Foo.getBalance());
        assertEquals(1, Foo.getTransactions().size());
    }

    @Test
    void testTransferOutMany() {
        BigDecimal testBalance = new BigDecimal ("25");
        Foo.transferOut("20", Bar.getName());
        Foo.transferOut("25", Bar.getName());
        Foo.transferOut("30", Bar.getName());

        assertEquals(testBalance, Foo.getBalance());
        assertEquals(3, Foo.getTransactions().size());
    }

    @Test
    void testTransferInOnce() {
        BigDecimal testBalance = new BigDecimal ("150");
        Bar.transferIn("50", Foo.getName());

        assertEquals(testBalance, Bar.getBalance());
        assertEquals(4, Bar.getNotifications().size());
        assertEquals(1, Bar.getTransactions().size());
    }

    @Test
    void testTransferInMany() {
        BigDecimal testBalance = new BigDecimal("250");
        Bar.transferIn("25", Foo.getName());
        Bar.transferIn("50", Foo.getName());
        Bar.transferIn("75", Foo.getName());

        assertEquals(testBalance, Bar.getBalance());
        assertEquals(6, Bar.getNotifications().size());
        assertEquals(3, Bar.getTransactions().size());
    }

    @Test
    void testTransactionHistory() {
        Foo.deposit("50");
        Foo.withdraw("50");
        Foo.transferIn("50", Bar.getName());
        Foo.transferOut("50", Bar.getName());
        Foo.deposit("50");
        Foo.withdraw("50");
        Foo.transferIn("50", Bar.getName());
        Foo.transferOut("50", Bar.getName());

        assertEquals(8, Foo.getTransactions().size());
    }
}
package model;

import exceptions.CannotLockAdminException;
import exceptions.authentication.AuthenticationFailedAccountLockedException;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class AccountTest {
    private Account foo;
    private Account bar;
    private Account admin;

    @Before
    public void runBefore() {
        foo = new Account("pass123", "Foo");
        bar = new Account("pass123", "Bar");

        admin = new Account("pass123", "Admin");
    }

    @Test
    public void testConstructor() {
        BigDecimal testBalance = new BigDecimal("100");

        assertEquals("pass123", foo.getPassword());
        assertEquals("Foo", foo.getName());
        assertEquals(testBalance, foo.getBalance());
        assertEquals(3, foo.getNotifications().size());
        assertEquals(0, foo.getTransactions().size());
        assertFalse(foo.getLock());
    }

    @Test
    public void testJsonReaderConstructor() {
        ArrayList<String> jsonNotifications = new ArrayList<>();
        ArrayList<String> jsonTransactions = new ArrayList<>();
        BigDecimal jsonBalance = new BigDecimal("50");
        Account jsonAccount =
                new Account("jsonpass", "Jason", "50", jsonNotifications, jsonTransactions, true);

        assertEquals("jsonpass", jsonAccount.getPassword());
        assertEquals("Jason", jsonAccount.getName());
        assertEquals(jsonBalance, jsonAccount.getBalance());
        assertEquals(0, jsonAccount.getNotifications().size());
        assertEquals(0, jsonAccount.getTransactions().size());
        assertTrue(jsonAccount.getLock());

    }

    @Test
    public void testGetBalanceString() {
        bar.withdraw("100");

        assertEquals("0.00", bar.getBalanceString().substring(1));
        assertEquals("100.00", foo.getBalanceString().substring(1));

    }

    @Test
    public void testDepositOnce() {
        BigDecimal testBalance = new BigDecimal("150");
        foo.deposit("50");

        assertEquals(testBalance, foo.getBalance());
        assertEquals(1, foo.getTransactions().size());
    }

    @Test
    public void testDepositMany() {
        BigDecimal testBalance = new BigDecimal("250");
        foo.deposit("25");
        foo.deposit("50");
        foo.deposit("75");

        assertEquals(testBalance, foo.getBalance());
        assertEquals(3, foo.getTransactions().size());
    }

    @Test
    public void testWithdrawOnce() {
        BigDecimal testBalance = new BigDecimal("50");
        foo.withdraw("50");

        assertEquals(testBalance, foo.getBalance());
        assertEquals(1, foo.getTransactions().size());
    }

    @Test
    public void testWithdrawMany() {
        BigDecimal testBalance = new BigDecimal("25");
        foo.withdraw("20");
        foo.withdraw("25");
        foo.withdraw("30");

        assertEquals(testBalance, foo.getBalance());
        assertEquals(3, foo.getTransactions().size());
    }

    @Test
    public void testLoginNotificationsClear() {
        foo.loginNotifications();

        assertEquals(0, foo.getNotifications().size());
    }

    @Test
    public void testTransferOutOnce() {
        BigDecimal testBalance = new BigDecimal("50");
        foo.transferOut("50", bar.getName());

        assertEquals(testBalance, foo.getBalance());
        assertEquals(1, foo.getTransactions().size());
    }

    @Test
    public void testTransferOutMany() {
        BigDecimal testBalance = new BigDecimal ("25");
        foo.transferOut("20", bar.getName());
        foo.transferOut("25", bar.getName());
        foo.transferOut("30", bar.getName());

        assertEquals(testBalance, foo.getBalance());
        assertEquals(3, foo.getTransactions().size());
    }

    @Test
    public void testTransferInOnce() {
        BigDecimal testBalance = new BigDecimal ("150");
        bar.transferIn("50", foo.getName());

        assertEquals(testBalance, bar.getBalance());
        assertEquals(4, bar.getNotifications().size());
        assertEquals(1, bar.getTransactions().size());
    }

    @Test
    public void testTransferInMany() {
        BigDecimal testBalance = new BigDecimal("250");
        bar.transferIn("25", foo.getName());
        bar.transferIn("50", foo.getName());
        bar.transferIn("75", foo.getName());

        assertEquals(testBalance, bar.getBalance());
        assertEquals(6, bar.getNotifications().size());
        assertEquals(3, bar.getTransactions().size());
    }

    @Test
    public void testTransactionHistory() {
        foo.deposit("50");
        foo.withdraw("50");
        foo.transferIn("50", bar.getName());
        foo.transferOut("50", bar.getName());
//        String testTransactionsFoo = "Transaction history for Foo:\n\n"
//                + "[1] Deposit: $50.00\n     Balance: $150.00\n\n"
//                + "[2] Withdrawal: $50.00\n     Balance: $100.00\n\n"
//                + "[3] Incoming eTransfer: $50.00 from Bar\n     Balance: $150.00\n\n"
//                + "[4] Outgoing eTransfer: $50.00 to Bar\n     Balance: $100.00\n\n";

        assertEquals(4, foo.getTransactions().size());
//        assertEquals(testTransactionsFoo, foo.transactionHistory());
        assertEquals("Transaction history for Bar:\n\nNo transactions to show.", bar.transactionHistory());

    }

    @Test
    public void testLockAccountAdmin() {
        try {
            admin.lockAccount();
            fail("Cannot lock admin");
        } catch (CannotLockAdminException ex) {
            // pass
        }
    }

    @Test
    public void testLockAccountUser() {
        try {
            foo.lockAccount();
            assertTrue(foo.getLock());
            // pass
        } catch (CannotLockAdminException ex) {
            fail("Account is not admin");
        }
    }

    @Test
    public void testUnlockAccountUser() {
        try {
            foo.lockAccount();
        } catch (CannotLockAdminException ex) {
            fail("Account is not admin");
        }
        foo.unlockAccount();
        assertFalse(foo.getLock());
    }

    @Test
    public void testConfirmAccountNotLockedThrows() {
        try {
            foo.lockAccount();
            foo.confirmAccountNotLocked();
            fail("Account is locked");
        } catch (CannotLockAdminException ex) {
            fail("Account is not admin");
        } catch (AuthenticationFailedAccountLockedException ex) {
            // pass
        }
    }

    @Test
    public void testConfirmAccountNotLockedDoesNotThrow() {
        try {
            foo.confirmAccountNotLocked();
            // pass
        } catch (AuthenticationFailedAccountLockedException ex) {
            fail("Account is locked");
        }
    }
}
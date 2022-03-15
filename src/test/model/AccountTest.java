package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountTest {
    private Account foo;
    private Account bar;

    @BeforeEach
    void runBefore() {
        foo = new Account("pass123", "Foo");
        bar = new Account("pass123", "Bar");
    }

    @Test
    void testConstructor() {
        BigDecimal testBalance = new BigDecimal("100");

        assertEquals("pass123", foo.getPassword());
        assertEquals("Foo", foo.getName());
        assertEquals(testBalance, foo.getBalance());
        assertEquals(3, foo.getNotifications().size());
        assertEquals(0, foo.getTransactions().size());
    }

    @Test
    void testGetBalanceString() {
        bar.withdraw("100");

        assertEquals("$0.00", bar.getBalanceString());
        assertEquals("$100.00", foo.getBalanceString());

    }

    @Test
    void testDepositOnce() {
        BigDecimal testBalance = new BigDecimal("150");
        foo.deposit("50");

        assertEquals(testBalance, foo.getBalance());
        assertEquals(1, foo.getTransactions().size());
    }

    @Test
    void testDepositMany() {
        BigDecimal testBalance = new BigDecimal("250");
        foo.deposit("25");
        foo.deposit("50");
        foo.deposit("75");

        assertEquals(testBalance, foo.getBalance());
        assertEquals(3, foo.getTransactions().size());
    }

    @Test
    void testWithdrawOnce() {
        BigDecimal testBalance = new BigDecimal("50");
        foo.withdraw("50");

        assertEquals(testBalance, foo.getBalance());
        assertEquals(1, foo.getTransactions().size());
    }

    @Test
    void testWithdrawMany() {
        BigDecimal testBalance = new BigDecimal("25");
        foo.withdraw("20");
        foo.withdraw("25");
        foo.withdraw("30");

        assertEquals(testBalance, foo.getBalance());
        assertEquals(3, foo.getTransactions().size());
    }

    @Test
    void testLoginNotificationsClear() {
        foo.loginNotifications();

        assertEquals(0, foo.getNotifications().size());
    }

    @Test
    void testTransferOutOnce() {
        BigDecimal testBalance = new BigDecimal("50");
        foo.transferOut("50", bar.getName());

        assertEquals(testBalance, foo.getBalance());
        assertEquals(1, foo.getTransactions().size());
    }

    @Test
    void testTransferOutMany() {
        BigDecimal testBalance = new BigDecimal ("25");
        foo.transferOut("20", bar.getName());
        foo.transferOut("25", bar.getName());
        foo.transferOut("30", bar.getName());

        assertEquals(testBalance, foo.getBalance());
        assertEquals(3, foo.getTransactions().size());
    }

    @Test
    void testTransferInOnce() {
        BigDecimal testBalance = new BigDecimal ("150");
        bar.transferIn("50", foo.getName());

        assertEquals(testBalance, bar.getBalance());
        assertEquals(4, bar.getNotifications().size());
        assertEquals(1, bar.getTransactions().size());
    }

    @Test
    void testTransferInMany() {
        BigDecimal testBalance = new BigDecimal("250");
        bar.transferIn("25", foo.getName());
        bar.transferIn("50", foo.getName());
        bar.transferIn("75", foo.getName());

        assertEquals(testBalance, bar.getBalance());
        assertEquals(6, bar.getNotifications().size());
        assertEquals(3, bar.getTransactions().size());
    }

    @Test
    void testTransactionHistory() {
        foo.deposit("50");
        foo.withdraw("50");
        foo.transferIn("50", bar.getName());
        foo.transferOut("50", bar.getName());
        String testTransactionsFoo = "Transaction history for Foo:\n"
                + "[1] Deposit: $50.00 | Balance: $150.00\n"
                + "[2] Withdrawal: $50.00 | Balance: $100.00\n"
                + "[3] Incoming eTransfer: $50.00 from Bar | Balance: $150.00\n"
                + "[4] Outgoing eTransfer: $50.00 to Bar | Balance: $100.00\n";

        assertEquals(4, foo.getTransactions().size());
        assertEquals(testTransactionsFoo, foo.transactionHistory());
        assertEquals("Transaction history for Bar:\nNo transactions to show.", bar.transactionHistory());

    }
}
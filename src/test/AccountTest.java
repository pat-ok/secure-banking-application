package test;

import main.model.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountTest {
    private Account testAccount;

    ArrayList<String> testInbox = new ArrayList<>();

    @BeforeEach
    void runBefore() {
        testInbox.add("This is a test for inbox.");
        testAccount = new Account("password123", "John", 300, testInbox);
    }

    @Test
    void testConstructor() {
        assertEquals("password123", testAccount.getPassword());
        assertEquals("John", testAccount.getName());
        assertEquals(300, testAccount.getBalance());
        assertEquals(testInbox, testAccount.getInbox());
    }

    @Test
    void testDeposit() {
        testAccount.deposit(100);
        assertEquals(400, testAccount.getBalance());
    }

    @Test
    void testWithdraw() {
        testAccount.withdraw(100);
        assertEquals(200, testAccount.getBalance());
    }
}
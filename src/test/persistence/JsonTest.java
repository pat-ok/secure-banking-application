package persistence;

import model.Account;
import model.UserDatabase;

import java.math.BigDecimal;
import java.util.HashMap;

import static model.Security.hashFunction;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {

    protected void checkAccount(UserDatabase userDatabase, String user, String password, String name, String balance) {
        HashMap<String, Account> testUserInfo = userDatabase.getUserDatabase();
        Account testAccount = testUserInfo.get(user);

        checkPassword(testAccount, password);
        checkName(testAccount, name);
        checkBalance(testAccount, balance);

    }

    private void checkPassword(Account account, String password) {
        String salt = account.getPassword().substring(0, 5);
        int hash = hashFunction(salt + password);
        String hashedPass = salt + hash;

        assertEquals(hashedPass, account.getPassword());
    }

    protected void checkName(Account account, String name) {
        assertEquals(name, account.getName());
    }

    protected void checkBalance(Account account, String balance) {
        BigDecimal testBalance = new BigDecimal(balance);

        assertEquals(testBalance, account.getBalance());
    }
}

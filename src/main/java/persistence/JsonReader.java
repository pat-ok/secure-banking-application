package persistence;

import model.Account;
import model.UserDatabase;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

// Represents a reader that reads user database from JSON data stored in file
public class JsonReader {
    private final String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads user database from file and returns it;
    // throws IOException if an error occurs reading data from file
    public UserDatabase read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseUserDatabase(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: parses user database from JSON object and returns it
    private UserDatabase parseUserDatabase(JSONObject jsonObject) {
        // instantiate new database
        UserDatabase udb = new UserDatabase(false);

        // transfer json keys into arraylist of usernames
        ArrayList<String> usernames = jsonToUsernames(jsonObject);

        // transfer json values into arraylist of accounts
        ArrayList<Account> accounts = jsonToAccounts(jsonObject);

        // with an arraylist of usernames and an arraylist of accounts,
        // iterate through each concurrently to add to user database
        for (int i = 0; i < usernames.size(); i++) {
            udb.storeAccount(usernames.get(i), accounts.get(i));
        }
        return udb;
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: helper method that creates an arraylist of usernames from source file
    private ArrayList<String> jsonToUsernames(JSONObject jsonObject) {
        JSONArray jsonKeyArray = jsonObject.getJSONArray("keys");
        ArrayList<String> usernames = new ArrayList<>();
        for (Object jsonUsername : jsonKeyArray) {
            String username = jsonUsername.toString();
            usernames.add(username);
        }
        return usernames;
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
    // EFFECTS: helper method that creates an arraylist of accounts from source file
    private ArrayList<Account> jsonToAccounts(JSONObject jsonObject) {
        JSONArray jsonValueArray = jsonObject.getJSONArray("values");
        ArrayList<Account> accounts = new ArrayList<>();

        for (Object jsonAccount : jsonValueArray) {
            JSONObject nextAccount = (JSONObject) jsonAccount;
            String pass = nextAccount.getString("password");
            String name = nextAccount.getString("name");
            String balance = nextAccount.getString("balanceString");
            boolean lock = nextAccount.getBoolean("lock");

            ArrayList<String> notifications = new ArrayList<>();
            JSONArray jsonNotifications = nextAccount.getJSONArray("notifications");
            for (Object jsonNotification : jsonNotifications) {
                notifications.add(jsonNotification.toString());
            }

            ArrayList<String> transactions = new ArrayList<>();
            JSONArray jsonTransactions = nextAccount.getJSONArray("transactions");
            for (Object jsonTransaction : jsonTransactions) {
                transactions.add(jsonTransaction.toString());
            }
            accounts.add(new Account(pass, name, balance, notifications, transactions, lock));
        }
        return accounts;
    }
}

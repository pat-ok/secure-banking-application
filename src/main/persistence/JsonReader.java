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
import java.util.List;
import java.util.stream.Stream;

// Represents a reader that reads user database from JSON data stored in file
public class JsonReader {
    private String source;

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
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // TODO: make this smaller
    // EFFECTS: parses user database from JSON object and returns it
    private UserDatabase parseUserDatabase(JSONObject jsonObject) {
        // instantiate new database
        UserDatabase udb = new UserDatabase(false);

        // transfer json keys into arraylist of usernames
        JSONArray jsonKeyArray = jsonObject.getJSONArray("keys");
        List<String> usernames = new ArrayList<String>();
        for (Object jsonUsername : jsonKeyArray) {
            String username = jsonUsername.toString();
            usernames.add(username);
        }

        // transfer json values into arraylist of accounts
        JSONArray jsonAccountArray = jsonObject.getJSONArray("values");
        List<Account> accounts = new ArrayList<Account>();
        for (Object jsonAccount : jsonAccountArray) {
            JSONObject nextAccount = (JSONObject) jsonAccount;
            String pass = nextAccount.getString("password");
            String name = nextAccount.getString("name");
            String balance = nextAccount.getString("balanceString");

            // create arraylist of notifications
            ArrayList<String> notifications = new ArrayList<String>();
            JSONArray jsonNotifications = nextAccount.getJSONArray("notifications");
            for (Object jsonNotification : jsonNotifications) {
                notifications.add(jsonNotification.toString());
            }

            // create arraylist of transactions
            ArrayList<String> transactions = new ArrayList<String>();
            JSONArray jsonTransactions = nextAccount.getJSONArray("transactions");
            for (Object jsonTransaction : jsonTransactions) {
                transactions.add(jsonTransaction.toString());
            }

            // add account to arraylist of accounts
            accounts.add(new Account(pass, name, balance, notifications, transactions));
        }

        // with an arraylist of usernames and an arraylist of accounts,
        // iterate through each equally to add to user database
        assert (usernames.size() == accounts.size());
        for (int i = 0; i < usernames.size(); i++) {
            udb.storeAccount(usernames.get(i), accounts.get(i));
        }
        return udb;
    }
}

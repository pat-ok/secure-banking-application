package ui;

import model.Account;
import model.UserDatabase;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

// Represents the Banking Application
public class BankingApp {
    private static final String JSON_STORE = "./data/database.json";
    private final JsonWriter jsonWriter;
    private final JsonReader jsonReader;
    private Scanner input;

    private UserDatabase database;
    private final HashMap<String, Account> databaseInfo;
    private String username;

    protected static final int WIDTH = 1000;
    protected static final int HEIGHT = 800;

    protected JFrame frame;
    protected static JPanel container;
    protected JPanel registrationPanel;
    protected JButton buttonRegister;
    protected JButton buttonHaveAccount;
    protected JLabel registrationTitle;
    protected static CardLayout cl;


    public static Font makeFont(int size) {
        return new Font("Arial", Font.PLAIN, size);
    }

    // EFFECTS: initiates Banking Application
    public BankingApp() {
        // setting up JFrame
        frame = new JFrame("Banking Application");
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // parent container
        container = new JPanel();
        cl = new CardLayout();
        container.setLayout(cl);

        // registration child panel
        JPanel registerPanel = new RegisterPanel();
        container.add(registerPanel, "register");

        // login child panel
        JPanel loginPanel = new LoginPanel();
        container.add(loginPanel, "login");

        // card layout setup
        cl.show(container, "registration");
        frame.add(container);

        frame.setVisible(true);






        jsonReader = new JsonReader(JSON_STORE);

        // try to load database from save when opening,
        // otherwise create new database with 2 demo accounts (foo, bar)
        try {
            loadUserDatabase();
            System.out.println("File loaded from " + JSON_STORE);
        } catch (IOException ioe) {
            database = new UserDatabase(true);
            System.out.println(JSON_STORE + " not found; creating new database");
        }

        databaseInfo = database.getUserDatabase();

        // save database when closing
        jsonWriter = new JsonWriter(JSON_STORE);
        saveUserDatabase();
    }

    // EFFECTS: saves user database to file
    private void saveUserDatabase() {
        try {
            jsonWriter.open();
            jsonWriter.write(database);
            jsonWriter.close();
            System.out.println("Saved user database to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads user database from file
    private void loadUserDatabase() throws IOException {
        database = jsonReader.read();
    }
}

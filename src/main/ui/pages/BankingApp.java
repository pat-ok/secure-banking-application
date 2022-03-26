package ui.pages;

import model.Account;
import model.UserDatabase;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

// Represents the Banking Application
public class BankingApp {
    private static final String JSON_STORE = "./data/database.json";
    private JsonWriter jsonWriter;
    private final JsonReader jsonReader;
    private Scanner input;

    protected UserDatabase database;
    protected final HashMap<String, Account> databaseInfo;
    private String username;

    protected static final int WIDTH = 1000;
    protected static final int HEIGHT = 650;

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


        // setting up JFrame
        frame = new JFrame("Banking Application");
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                // save database when closing
                jsonWriter = new JsonWriter(JSON_STORE);
                saveUserDatabase();
            }
        });

        // parent container
        container = new JPanel();
        cl = new CardLayout();
        container.setLayout(cl);

        // child registration panel
        JPanel registerPanel = new RegisterPanel(database);
        container.add(registerPanel, "register");

        // child login container panel
        JPanel loginPanel = new LoginPanel(database);
        container.add(loginPanel, "login");

        // hack child panel
//        JPanel hackPanel = new HackPanel();
//        container.add(hackPanel, "hack");

        // card layout setup
        cl.show(container, "registration");
        frame.add(container);

        frame.setVisible(true);
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

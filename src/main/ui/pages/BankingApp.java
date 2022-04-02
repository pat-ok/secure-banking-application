package ui.pages;

import model.UserDatabase;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

// Represents the Banking Application
public class BankingApp {
    private static final String JSON_STORE = "./data/database.json";
    public static final Color whitish = new Color(235, 235, 235);
    public static final Color bluish = new Color(17, 70, 144);
    protected static final int WIDTH = 1000;
    protected static final int HEIGHT = 650;

    protected UserDatabase database;

    protected JFrame frame;
    protected static JPanel container;
    protected static CardLayout cl;

    // EFFECTS: Initiates Banking Application
    public BankingApp() {
        // Option to load from save
        loadUserDatabaseOption();
    }

    // EFFECTS: Initiates main window and elements
    private void initiateFrame() {
        // frame setup
        frame = new JFrame("Banking Application");
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                saveUserDatabaseOption();
            }
        });

        // container panel as card layout
        container = new JPanel();
        cl = new CardLayout();
        container.setLayout(cl);

        // child registration panel
        JPanel registerPanel = new RegisterPanel(database);
        container.add(registerPanel, "register page");

        // child login panel
        JPanel loginPanel = new LoginPanel(database);
        container.add(loginPanel, "login page");

        // parent card layout setup
        cl.show(container, "register page");
        frame.add(container);

        frame.setVisible(true);
    }

    // DATA PERSISTENCE ================================================================================================
    // EFFECTS: Prompts user for choice to load from saved file
    private void loadUserDatabaseOption() {
        int choice = JOptionPane.showConfirmDialog(null, "Do you want to load from save?");
        switch (choice) {
            case JOptionPane.YES_OPTION:
                loadUserDatabase();
                initiateFrame();
                break;
            case JOptionPane.NO_OPTION:
                database = new UserDatabase(true);
                optionPane("Creating new database.");
                initiateFrame();
                break;
            default:
        }
    }

    // EFFECTS: Loads user database from file
    private void loadUserDatabase() {
        JsonReader jsonReader = new JsonReader(JSON_STORE);
        try {
            database = jsonReader.read();
            optionPane("Database loaded from: " + JSON_STORE);
        } catch (IOException ex) {
            optionPane("Unable to load database. Creating new save.");
        }
    }

    // EFFECTS: Prompts user for choice to save to file
    private void saveUserDatabaseOption() {
        int choice = JOptionPane.showConfirmDialog(null, "Do you want to save to file?");
        switch (choice) {
            case JOptionPane.YES_OPTION:
                saveUserDatabase();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                break;
            case JOptionPane.NO_OPTION:
                optionPane("Database not saved.");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                break;
            default:
                frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        }
    }

    // EFFECTS: Saves user database to file
    private void saveUserDatabase() {
        JsonWriter jsonWriter = new JsonWriter(JSON_STORE);
        try {
            jsonWriter.open();
            jsonWriter.write(database);
            jsonWriter.close();
            optionPane("Database saved to: " + JSON_STORE);
        } catch (FileNotFoundException e) {
            optionPane("Unable to write to file: " + JSON_STORE);
        }
    }

    // HELPER METHODS ==================================================================================================
    // EFFECTS: Creates a pop-up JOptionPane with a message
    public static void optionPane(String message) {
        JOptionPane.showMessageDialog(null, message, "Banking Application", JOptionPane.WARNING_MESSAGE);
    }

    // EFFECTS: Creates a plain Segoe UI font in specified size
    public static Font makeFont(int size) {
        return new Font("Segoe UI", Font.PLAIN, size);
    }

    // EFFECTS: Creates a bold Segoe UI font in specified size
    public static Font makeFontBold(int size) {
        return new Font("Segoe UI", Font.BOLD, size);
    }
}

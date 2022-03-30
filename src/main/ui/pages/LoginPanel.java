package ui.pages;

import exceptions.AuthenticationFailedException;
import model.Account;
import model.UserDatabase;
import ui.modern.JButtonModern;
import ui.modern.JLabelModern;
import ui.modern.JTextFieldModern;

import javax.swing.*;
import java.awt.*;

import static ui.pages.BankingApp.*;

// Represents authentication UI for account login authentication
// Child panel of LoginPanel card layout
public class LoginPanel extends JPanel {

    private final int width = BankingApp.WIDTH;
    private final UserDatabase udb;

    // FIELDS
    private JTextField username;
    private JTextField password;

    // Constructor for authentication panel
    public LoginPanel(UserDatabase udb) {
        this.setLayout(null);
        this.setBackground(whitish);
        this.udb = udb;

        createTitle();
        createUsername();
        createPassword();
        createLoginButton();
        createRegisterButton();
    }

    // ELEMENT CREATION ================================================================================================
    // EFFECTS: Creates login title label
    private void createTitle() {
        JLabel loginTitle = new JLabelModern("Login to your account!");
        loginTitle.setBounds(width / 2 - 110, 130, 300, 40);
        loginTitle.setFont(makeFont(23));
        this.add(loginTitle);
    }

    // EFFECTS: Creates username entry field with label
    private void createUsername() {
        username = new JTextFieldModern("Username");
        username.setBounds(width / 2 - 100, 220, 200, 35);
        this.add(username);
    }

    // EFFECTS: Creates password entry field with label
    private void createPassword() {
        password = new JTextFieldModern("Password");
        password.setBounds(width / 2 - 100, 290, 200, 35);
        this.add(password);
    }

    // EFFECTS: Creates login button triggering account authentication
    private void createLoginButton() {
        JButton buttonLogin = new JButtonModern("LOGIN");
        buttonLogin.setLocation(width / 2 - 100, 390);
        buttonLogin.addActionListener(arg0 -> {
            try {
                udb.authUsername(username.getText());
                udb.authPassword(username.getText(), password.getText());
                JPanel accountPanel;
                Account account = udb.getUserDatabase().get(username.getText());
                if (username.getText().equals("admin")) {
                    accountPanel = new AccountPanel(udb, account, true);
                } else {
                    accountPanel = new AccountPanel(udb, account, false);
                }
                container.add(accountPanel, "account");
                cl.show(container, "account");
                clearFields();
            } catch (AuthenticationFailedException afe) {
                optionPane("Username or password is incorrect!");
            }
        });
        this.add(buttonLogin);
    }

    // EFFECTS: Creates registration button bringing user back to registration panel
    private void createRegisterButton() {
        JLabel textDoNotHaveAccount = new JLabelModern("Don't have an account?");
        textDoNotHaveAccount.setBounds(width / 2 - 300, 500, 200, 35);
        this.add(textDoNotHaveAccount);

        JButton buttonRegister = new JButtonModern("SIGN UP");
        buttonRegister.setLocation(width / 2 - 100, 500);
        buttonRegister.addActionListener(arg0 -> {
            cl.show(container, "register");
            clearFields();
        });
        this.add(buttonRegister);
    }

    // HELPER METHODS ==================================================================================================
    // EFFECTS: Clears user input from fields and resets to default prompts
    private void clearFields() {
        username.setText("Username");
        username.setForeground(Color.GRAY);
        password.setText("Password");
        password.setForeground(Color.GRAY);
    }
}

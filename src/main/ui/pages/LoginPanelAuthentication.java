package ui.pages;

import exceptions.AuthenticationFailedException;
import model.Account;
import model.UserDatabase;

import javax.swing.*;
import java.awt.*;

import static ui.pages.BankingApp.*;
import static ui.pages.LoginPanel.lcl;

public class LoginPanelAuthentication extends JPanel {

    private final int width = BankingApp.WIDTH;
    private final UserDatabase udb;
    private final JPanel parentContainer;

    // FIELDS
    private JTextField username;
    private JTextField password;

    // Constructor for authentication panel
    public LoginPanelAuthentication(UserDatabase udb, JPanel parentContainer) {
        this.setLayout(null);
        this.setBackground(Color.CYAN);
        this.udb = udb;
        this.parentContainer = parentContainer;

        createTitle();
        createUsername();
        createPassword();
        createLoginButton();
        createRegisterButton();
    }

    // EFFECTS: Creates login title label
    private void createTitle() {
        JLabel loginTitle = new JLabel("Login to your account!");
        loginTitle.setBounds(width / 2 - 110, 130, 300, 40);
        loginTitle.setFont(makeFont(23));
        this.add(loginTitle);
    }

    // EFFECTS: Creates username entry field with label
    private void createUsername() {
        username = new JTextField("");
        username.setBounds(width / 2 - 100, 220, 200, 35);
        this.add(username);

        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setBounds(width / 2 - 100, 200, 100, 20);
        usernameLabel.setFont(makeFont(12));
        this.add(usernameLabel);
    }

    // EFFECTS: Creates password entry field with label
    private void createPassword() {
        password = new JTextField("");
        password.setBounds(width / 2 - 100, 290, 200, 35);
        this.add(password);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(width / 2 - 100, 270, 100, 20);
        passwordLabel.setFont(makeFont(12));
        this.add(passwordLabel);
    }

    // EFFECTS: Creates login button triggering account authentication
    private void createLoginButton() {
        JButton buttonLogin = new JButton("Login");
        buttonLogin.setBounds(width / 2 - 50, 390, 100, 35);
        buttonLogin.addActionListener(arg0 -> {
            try {
                udb.authUsername(username.getText());
                udb.authPassword(username.getText(), password.getText());
                JPanel accountPanel;
                Account account = udb.getUserDatabase().get(username.getText());
                if (username.getText().equals("admin")) {
                    accountPanel = new LoginPanelAccount(udb, account, true);
                } else {
                    accountPanel = new LoginPanelAccount(udb, account, false);
                }
                parentContainer.add(accountPanel, "account");
                lcl.show(parentContainer, "account");
                clearFields();
            } catch (AuthenticationFailedException afe) {
                optionPane(afe.getMessage());
            }
        });
        this.add(buttonLogin);
    }

    // EFFECTS: Creates registration button bringing user back to registration panel
    private void createRegisterButton() {
        JLabel textDoNotHaveAccount = new JLabel("Don't have an account?");
        textDoNotHaveAccount.setBounds(width / 2 - 220, 440, 200, 35);
        this.add(textDoNotHaveAccount);

        JButton buttonRegister = new JButton("Register");
        buttonRegister.setBounds(width / 2 - 50, 440, 100, 35);
        buttonRegister.addActionListener(arg0 -> {
            clearFields();
            cl.show(container, "register");
        });
        this.add(buttonRegister);
    }

    // EFFECTS: Clears user input from all fields
    private void clearFields() {
        username.setText("");
        password.setText("");
    }
}

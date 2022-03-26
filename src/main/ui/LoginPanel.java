package ui;

import model.UserDatabase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static ui.BankingApp.*;
import static ui.LoginPanelContainer.lcl;

public class LoginPanel extends JPanel {

    private final int width = BankingApp.WIDTH;

    public LoginPanel(UserDatabase udb, JPanel parentContainer) {
        super.setLayout(null);
        super.setBounds(0, 0, 600, 600);
        super.setBackground(Color.CYAN);


        // adding title
        JLabel loginTitle = new JLabel("Login to your account!");
        loginTitle.setBounds(width / 2 - 110, 130, 300, 40);
        loginTitle.setFont(makeFont(23));
        super.add(loginTitle);


        // USERNAME
        // adding username text field
        JTextField username = new JTextField("");
        username.setBounds(width / 2 - 100, 220, 200, 35);
        super.add(username);

        // adding username text field label
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setBounds(width / 2 - 100, 200, 100, 20);
        usernameLabel.setFont(makeFont(12));
        super.add(usernameLabel);


        // PASSWORD
        // adding password text field
        JTextField password = new JTextField("");
        password.setBounds(width / 2 - 100, 290, 200, 35);
        super.add(password);

        // adding password text field label
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(width / 2 - 100, 270, 100, 20);
        passwordLabel.setFont(makeFont(12));
        super.add(passwordLabel);


        // LOGIN BUTTON
        JButton buttonLogin = new JButton("Login");
        buttonLogin.setBounds(width / 2 - 50, 390, 100, 35);
        buttonLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                try {
                    udb.authUsername(username.getText());
                    udb.authPassword(username.getText(), password.getText(), 3);
                    cl.show(container, "account");
                    // account child split pane
                    JPanel accountStatusPanel = new AccountStatusPanel();
                    JPanel accountChoicePanel = new AccountChoicePanel();
                    JSplitPane accountPanel = new AccountPanel(udb.getUserDatabase().get(username.getText()), accountStatusPanel, accountChoicePanel);
                    parentContainer.add(accountPanel, "account");
                    lcl.show(parentContainer, "account");

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Login Failed!", "Banking Application", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        super.add(buttonLogin);

        // adding do not have an account text
        JLabel textDoNotHaveAccount = new JLabel("Don't have an account?");
        textDoNotHaveAccount.setBounds(width / 2 - 220, 440, 200, 35);
        super.add(textDoNotHaveAccount);

        // REGISTRATION BUTTON
        JButton buttonRegister = new JButton("Register");
        buttonRegister.setBounds(width / 2 - 50, 440, 100, 35);
        buttonRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                cl.show(container, "register");
            }
        });
        super.add(buttonRegister);
    }
}

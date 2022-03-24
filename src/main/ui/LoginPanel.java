package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static ui.BankingApp.*;

public class LoginPanel extends JPanel {

    private final int width = BankingApp.WIDTH;

    public LoginPanel() {
        super.setLayout(null);
        super.setBounds(0, 0, 600, 600);
        super.setBackground(Color.CYAN);


        // adding title
        JLabel loginTitle = new JLabel("Login to your account!");
        loginTitle.setBounds(width / 2 - 100, 30, 300, 40);
        loginTitle.setFont(makeFont(23));
        super.add(loginTitle);


        // USERNAME
        // adding username text field
        JTextField username = new JTextField("");
        username.setBounds(width / 2 - 100, 120, 200, 35);
        super.add(username);

        // adding username text field label
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setBounds(width / 2 - 100, 100, 100, 20);
        usernameLabel.setFont(makeFont(12));
        super.add(usernameLabel);


        // PASSWORD
        // adding password text field
        JTextField password = new JTextField("");
        password.setBounds(width / 2 - 100, 190, 200, 35);
        super.add(password);

        // adding password text field label
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(width / 2 - 100, 170, 100, 20);
        passwordLabel.setFont(makeFont(12));
        super.add(passwordLabel);


        // LOGIN BUTTON
        JButton buttonLogin = new JButton("Login");
        buttonLogin.setBounds(width / 2 - 50, 290, 100, 35);
        super.add(buttonLogin);

        // adding do not have an account text
        JLabel textDoNotHaveAccount = new JLabel("Don't have an account?");
        textDoNotHaveAccount.setBounds(width / 2 - 220, 340, 200, 35);
        super.add(textDoNotHaveAccount);

        // REGISTRATION BUTTON
        JButton buttonRegister = new JButton("Register");
        buttonRegister.setBounds(width / 2 - 50, 340, 100, 35);
        buttonRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                cl.show(container, "register");
            }
        });
        super.add(buttonRegister);
    }
}

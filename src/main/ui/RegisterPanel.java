package ui;

import exceptions.IllegalEntryException;
import exceptions.InvalidNameException;
import exceptions.PasswordsDoNotMatchException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static model.Formatting.*;
import static ui.BankingApp.*;

public class RegisterPanel extends JPanel {

    private final int width = BankingApp.WIDTH;

    public RegisterPanel() {
        super.setLayout(null);
        super.setBounds(0, 0, WIDTH, HEIGHT);
        super.setBackground(new Color(0, 140, 0));


        // adding title
        JLabel registrationTitle = new JLabel("Register for a new account!");
        registrationTitle.setBounds(width / 2 - 150, 30, 300, 40);
        registrationTitle.setFont(makeFont(23));
        super.add(registrationTitle);


        // NAME
        // adding name availability status
        JLabel newNameAvailability = new JLabel("");
        newNameAvailability.setBounds(width / 2 + 120, 120, 100, 35);
        super.add(newNameAvailability);

        // adding name text field
        JTextField newName = new JTextField("");
        newName.setBounds(width / 2 - 100, 120, 200, 35);
        newName.addActionListener(e -> {
            try {
                isValidName(newName.getText());
                newNameAvailability.setText("Available!");
            } catch (InvalidNameException ine) {
                newNameAvailability.setText("Unavailable!");
            }
        });
        super.add(newName);

        // adding name text field label
        JLabel newNameLabel = new JLabel("Name");
        newNameLabel.setBounds(width / 2 - 100, 100, 100, 20);
        newNameLabel.setFont(makeFont(12));
        super.add(newNameLabel);

        // USERNAME
        // adding username availability status
        JLabel newUsernameAvailability = new JLabel("");
        newUsernameAvailability.setBounds(width / 2 + 120, 190, 200, 35);
        super.add(newUsernameAvailability);

        // adding username text field
        JTextField newUsername = new JTextField("");
        newUsername.setBounds(width / 2 - 100, 190, 200, 35);
        newUsername.addActionListener(e -> {
            try {
                isValidEntry(newUsername.getText());
                newUsernameAvailability.setText("Available!");
            } catch (IllegalEntryException iee) {
                newUsernameAvailability.setText("Unavailable!");
            }
        });
        super.add(newUsername);

        // adding username text field label
        JLabel newUsernameLabel = new JLabel("Username");
        newUsernameLabel.setBounds(width / 2 - 100, 170, 100, 20);
        newUsernameLabel.setFont(makeFont(12));
        super.add(newUsernameLabel);


        // PASSWORD
        // adding password availability status
        JLabel newPasswordAvailability = new JLabel("");
        newPasswordAvailability.setBounds(width / 2 + 120, 260, 200, 35);
        super.add(newPasswordAvailability);

        // adding password text field
        JTextField newPassword = new JTextField("");
        newPassword.setBounds(width / 2 - 100, 260, 200, 35);
        newPassword.addActionListener(e -> {
            try {
                isValidEntry(newPassword.getText());
                newPasswordAvailability.setText("Valid password!");
            } catch (IllegalEntryException iee) {
                newPasswordAvailability.setText("Invalid password!");
            }
        });
        super.add(newPassword);

        // adding password text field label
        JLabel newPasswordLabel = new JLabel("Password");
        newPasswordLabel.setBounds(width / 2 - 100, 240, 100, 20);
        newPasswordLabel.setFont(makeFont(12));
        super.add(newPasswordLabel);


        // PASSWORD CONFIRMATION
        // adding password confirmation text field
        JLabel newPasswordConfirmationAvailability = new JLabel("");
        newPasswordConfirmationAvailability.setBounds(width / 2 + 120, 330, 200, 35);
        super.add(newPasswordConfirmationAvailability);

        // adding password confirmation text field
        JTextField newPasswordConfirmation = new JTextField("");
        newPasswordConfirmation.setBounds(width / 2 - 100, 330, 200, 35);
        newPasswordConfirmation.addActionListener(e -> {
            try {
                doPasswordsMatch(newPassword.getText(), newPasswordConfirmation.getText());
                newPasswordConfirmationAvailability.setText("Passwords Match!");
            } catch (PasswordsDoNotMatchException pdnme) {
                newPasswordConfirmationAvailability.setText("Passwords Do Not Match!");
            }
        });
        super.add(newPasswordConfirmation);

        // adding password confirmation text field label
        JLabel newPasswordConfirmationLabel = new JLabel("Confirm Password");
        newPasswordConfirmationLabel.setBounds(width / 2 - 100, 310, 200, 20);
        newPasswordConfirmationLabel.setFont(makeFont(12));
        super.add(newPasswordConfirmationLabel);

        // adding registration button
        JButton buttonRegister = new JButton("Register");
        buttonRegister.setBounds(width / 2 - 50, 430, 100, 35);
        super.add(buttonRegister);

        // adding account owned text
        JLabel textHaveAccount = new JLabel("Already have an account?");
        textHaveAccount.setBounds(width / 2 - 220, 480, 200, 35);
        super.add(textHaveAccount);

        // adding account owned login button
        JButton buttonHaveAccount = new JButton("Login");
        //buttonHaveAnAccount.setBounds(frame.getWidth() / 2, 100, 100, 35);
        buttonHaveAccount.setBounds(width / 2 - 50, 480, 100, 35);
        buttonHaveAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                cl.show(container, "login");
            }
        });
        super.add(buttonHaveAccount);
    }
}

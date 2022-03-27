package ui.pages;

import exceptions.RegistrationFailedException;
import model.Account;
import model.UserDatabase;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

import static model.Formatting.*;
import static model.Security.hashFunction;
import static model.Security.salt;
import static ui.pages.BankingApp.*;

// Represents registration UI for account creation
// Child panel of container panel from BankingApp
public class RegisterPanel extends JPanel {

    private final int width = BankingApp.WIDTH;
    private final UserDatabase udb;

    private JTextField newName;
    private JLabel newNameAvailability;
    private JTextField newUsername;
    private JLabel newUsernameAvailability;
    private JTextField newPassword;
    private JLabel newPasswordAvailability;
    private JTextField newPasswordConfirm;
    private JLabel newPasswordConfirmAvailability;
    private JLabel registerStatus;

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawRect(0, 0, 500, 500);
        g.setColor(Color.CYAN);
        g.fillRect(0, 0, 500, 500);
    }

    // Constructor for registration panel
    public RegisterPanel(UserDatabase udb) {
        this.setLayout(null);
        this.setBackground(new Color(0, 140, 0));
        this.udb = udb;


        createTitle();
        createNewName();
        createNewUsername();
        createNewPassword();
        createNewPasswordConfirm();
        createRegister();
        createLogin();
    }

    // ELEMENT CREATION ================================================================================================
    // Creates registration title label
    private void createTitle() {
        JLabel registrationTitle = new JLabel("Register for a new account!");
        registrationTitle.setBounds(width / 2 - 150, 30, 300, 40);
        registrationTitle.setFont(makeFont(23));
        this.add(registrationTitle);
    }

    // Creates new name text field and availability status label
    private void createNewName() {
        newNameAvailability = new JLabel("");
        newNameAvailability.setBounds(width / 2 + 120, 120, 100, 35);
        this.add(newNameAvailability);

        JLabel newNameLabel = new JLabel("Name");
        newNameLabel.setBounds(width / 2 - 100, 100, 100, 20);
        newNameLabel.setFont(makeFont(12));
        this.add(newNameLabel);

        newName = new JTextField("");
        newName.setBounds(width / 2 - 100, 120, 200, 35);
        this.add(newName);

        createNewNameUpdate();
    }

    // Adds new name text field availability functionality
    private void createNewNameUpdate() {
        newName.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent arg0) {}

            @Override
            public void insertUpdate(DocumentEvent arg0) {
                tryNewName();
            }

            @Override
            public void removeUpdate(DocumentEvent arg0) {
                tryNewName();
            }

            public void tryNewName() {
                try {
                    setRegisterClear();
                    isValidName(newName.getText());
                    setAvailable(newNameAvailability);
                } catch (RegistrationFailedException ex) {
                    setLabel(newNameAvailability, ex.getMessage());
                }
            }
        });
    }

    // Creates new username text field and availability status label
    private void createNewUsername() {
        newUsernameAvailability = new JLabel("");
        newUsernameAvailability.setBounds(width / 2 + 120, 190, 200, 35);
        this.add(newUsernameAvailability);

        JLabel newUsernameLabel = new JLabel("Username");
        newUsernameLabel.setBounds(width / 2 - 100, 170, 100, 20);
        newUsernameLabel.setFont(makeFont(12));
        this.add(newUsernameLabel);

        newUsername = new JTextField("");
        newUsername.setBounds(width / 2 - 100, 190, 200, 35);
        this.add(newUsername);

        createNewUsernameUpdate();
    }

    // Adds new username text field availability functionality
    private void createNewUsernameUpdate() {
        newUsername.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void changedUpdate(DocumentEvent arg0) {}

            @Override
            public void insertUpdate(DocumentEvent arg0) {
                tryNewUsername();
            }

            @Override
            public void removeUpdate(DocumentEvent arg0) {
                tryNewUsername();
            }

            private void tryNewUsername() {
                try {
                    setRegisterClear();
                    isValidEntry(newUsername.getText());
                    udb.isUsernameFree(newUsername.getText());
                    setAvailable(newUsernameAvailability);
                } catch (RegistrationFailedException ex) {
                    setLabel(newUsernameAvailability, ex.getMessage());
                }
            }
        });
    }

    // Creates new password text field and availability status label
    private void createNewPassword() {
        newPasswordAvailability = new JLabel("");
        newPasswordAvailability.setBounds(width / 2 + 120, 260, 200, 35);
        this.add(newPasswordAvailability);

        JLabel newPasswordLabel = new JLabel("Password");
        newPasswordLabel.setBounds(width / 2 - 100, 240, 100, 20);
        newPasswordLabel.setFont(makeFont(12));
        this.add(newPasswordLabel);

        newPassword = new JTextField("");
        newPassword.setBounds(width / 2 - 100, 260, 200, 35);
        this.add(newPassword);

        createNewPasswordUpdate();
    }

    // Adds new password text field availability functionality
    private void createNewPasswordUpdate() {
        newPassword.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent arg0) {}

            @Override
            public void insertUpdate(DocumentEvent arg0) {
                tryNewPassword();
            }

            @Override
            public void removeUpdate(DocumentEvent arg0) {
                tryNewPassword();
            }

            private void tryNewPassword() {
                try {
                    setRegisterClear();
                    isValidEntry(newPassword.getText());
                    setAvailable(newPasswordAvailability);
                } catch (RegistrationFailedException ex) {
                    setLabel(newPasswordAvailability, ex.getMessage());
                }
            }
        });
    }

    // Creates new password confirmation text field and availability status label
    private void createNewPasswordConfirm() {
        newPasswordConfirmAvailability = new JLabel("");
        newPasswordConfirmAvailability.setBounds(width / 2 + 120, 330, 200, 35);
        this.add(newPasswordConfirmAvailability);

        JLabel newPasswordConfirmationLabel = new JLabel("Confirm Password");
        newPasswordConfirmationLabel.setBounds(width / 2 - 100, 310, 200, 20);
        newPasswordConfirmationLabel.setFont(makeFont(12));
        this.add(newPasswordConfirmationLabel);

        newPasswordConfirm = new JTextField("");
        newPasswordConfirm.setBounds(width / 2 - 100, 330, 200, 35);
        this.add(newPasswordConfirm);

        createNewPasswordConfirmUpdate();
    }

    // Adds new password confirm text field availability functionality
    private void createNewPasswordConfirmUpdate() {
        newPasswordConfirm.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent arg0) {}

            @Override
            public void insertUpdate(DocumentEvent arg0) {
                tryNewPasswordConfirm();
            }

            @Override
            public void removeUpdate(DocumentEvent arg0) {
                tryNewPasswordConfirm();
            }

            private void tryNewPasswordConfirm() {
                try {
                    setRegisterClear();
                    doPasswordsMatch(newPassword.getText(), newPasswordConfirm.getText());
                    setAvailable(newPasswordConfirmAvailability);
                } catch (RegistrationFailedException ex) {
                    setLabel(newPasswordConfirmAvailability, ex.getMessage());
                }
            }
        });
    }

    // EFFECTS: Creates register button label
    private void createRegister() {
        registerStatus = new JLabel("");
        registerStatus.setBounds(width / 2 - 220, 430, 200, 35);
        this.add(registerStatus);
        createRegisterButton();
    }

    // EFFECTS: Creates register button with functionality
    private void createRegisterButton() {
        JButton buttonRegister = new JButton("REGISTER");
        buttonRegister.setBounds(width / 2 - 50, 430, 100, 35);
        buttonRegister.addActionListener(arg0 -> {
            try {
                isValidName(newName.getText());
                isValidEntry(newUsername.getText());
                udb.isUsernameFree(newUsername.getText());
                isValidEntry(newPassword.getText());
                doPasswordsMatch(newPassword.getText(), newPasswordConfirm.getText());

                Account acc = new Account(createPassword(newPassword.getText()), capitalizeName(newName.getText()));
                udb.storeAccount(newUsername.getText(), acc);
                clearFields();
                setLabel(registerStatus, "Account registered!");
                optionPane("Registration Success!");
            } catch (RegistrationFailedException ex) {
                optionPane("Registration Failed! One or more fields invalid!");
                setRegisterFailed();
            }
        });
        this.add(buttonRegister);
    }

    // EFFECTS: Creates login button and label
    //          login button switches user to login authentication panel
    private void createLogin() {
        JLabel textHaveAccount = new JLabel("Already have an account?");
        textHaveAccount.setBounds(width / 2 - 220, 480, 200, 35);
        this.add(textHaveAccount);

        JButton buttonHaveAccount = new JButton("Login");
        buttonHaveAccount.setBounds(width / 2 - 50, 480, 100, 35);
        buttonHaveAccount.addActionListener(arg0 -> {
            cl.show(container, "login");
            clearFields();
        });
        this.add(buttonHaveAccount);
    }

    // HELPER METHODS ==================================================================================================
    // EFFECTS: creates a new hashed password from a clear text password
    private String createPassword(String newPassword) {
        String salt = salt();
        return salt + hashFunction(salt + newPassword);
    }

    // EFFECTS: Sets label to available
    private void setAvailable(JLabel label) {
        label.setText("Available!");
    }

    // EFFECTS: Sets label to custom string
    private void setLabel(JLabel label, String message) {
        label.setText(message);
    }

    // EFFECTS: Sets registration status label to failed
    private void setRegisterFailed() {
        registerStatus.setText("Registration Failed!");
    }

    // EFFECTS: Clears registration status
    private void setRegisterClear() {
        registerStatus.setText("");
    }

    // EFFECTS: Clears user input from all fields
    private void clearFields() {
        newName.setText("");
        newNameAvailability.setText("");
        newUsername.setText("");
        newUsernameAvailability.setText("");
        newPassword.setText("");
        newPasswordAvailability.setText("");
        newPasswordConfirm.setText("");
        newPasswordConfirmAvailability.setText("");
    }
}

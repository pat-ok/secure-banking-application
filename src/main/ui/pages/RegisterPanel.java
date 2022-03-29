package ui.pages;

import exceptions.RegistrationFailedException;
import exceptions.RegistrationFailedMatchesHintException;
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

//    @Override
//    public void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        g.drawRect(0, 0, 500, 500);
//        g.setColor(Color.CYAN);
//        g.fillRect(0, 0, 500, 500);
//    }

    // Constructor for registration panel
    public RegisterPanel(UserDatabase udb) {
        this.setLayout(null);
        this.setBackground(new Color(235, 235, 235));
        this.udb = udb;


        createTitle();
        createNewName();
        createNewUsername();
        createNewPassword();
        createNewPasswordConfirm();
        createRegister();
        createLogin();

        repaint();
        revalidate();
    }

    // ELEMENT CREATION ================================================================================================
    // Creates registration title label
    private void createTitle() {
        JLabel registrationTitle = new JLabel("Register for a new account!");
        registrationTitle.setBounds(width / 2 - 50, 30, 300, 40);
        registrationTitle.setFont(new Font("Segoe UI", Font.BOLD, 23));
        this.add(registrationTitle);
    }

    // Creates new name text field and availability status label
    private void createNewName() {
        newNameAvailability = new JLabelModern("");
        newNameAvailability.setLocation(width / 2 + 220, 120);
        this.add(newNameAvailability);

        newName = new JTextFieldModern("Name");
        newName.setLocation(width / 2, 120);
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
                tryNewNameUpdate();
            }

            @Override
            public void removeUpdate(DocumentEvent arg0) {
                tryNewNameUpdate();
            }

            // EFFECTS: Checks if new name is available to use, if field is default then no status is shown
            private void tryNewNameUpdate() {
                try {
                    setRegisterClear();
                    isValidName(newName.getText());
                    matchesHintName(newName.getText());
                    setAvailable(newNameAvailability);
                } catch (RegistrationFailedException ex) {
                    setLabel(newNameAvailability, ex.getMessage());
                }
            }
        });
    }

    // Creates new username text field and availability status label
    private void createNewUsername() {
        newUsernameAvailability = new JLabelModern("");
        newUsernameAvailability.setLocation(width / 2 + 220, 185);
        this.add(newUsernameAvailability);

        newUsername = new JTextFieldModern("New username");
        newUsername.setLocation(width / 2, 185);
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
                tryNewUsernameUpdate();
            }

            @Override
            public void removeUpdate(DocumentEvent arg0) {
                tryNewUsernameUpdate();
            }

            // EFFECTS: Checks if new username is available to use, if field is default then no status is shown
            private void tryNewUsernameUpdate() {
                try {
                    setRegisterClear();
                    isValidEntry(newUsername.getText());
                    udb.isUsernameFree(newUsername.getText());
                    matchesHintUsername(newUsername.getText());
                    setAvailable(newUsernameAvailability);
                } catch (RegistrationFailedException ex) {
                    setLabel(newUsernameAvailability, ex.getMessage());
                }
            }
        });
    }

    // Creates new password text field and availability status label
    private void createNewPassword() {
        newPasswordAvailability = new JLabelModern("");
        newPasswordAvailability.setLocation(width / 2 + 220, 250);
        this.add(newPasswordAvailability);

        newPassword = new JTextFieldModern("New password");
        newPassword.setLocation(width / 2, 250);
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
                tryNewPasswordUpdate();
            }

            @Override
            public void removeUpdate(DocumentEvent arg0) {
                tryNewPasswordUpdate();
            }

            // EFFECTS: Checks if new password is available to use, if field is default then no status is shown
            private void tryNewPasswordUpdate() {
                try {
                    setRegisterClear();
                    isValidEntry(newPassword.getText());
                    matchesHintPassword(newPassword.getText());
                    setAvailable(newPasswordAvailability);
                } catch (RegistrationFailedException ex) {
                    setLabel(newPasswordAvailability, ex.getMessage());
                }
            }
        });
    }

    // Creates new password confirmation text field and availability status label
    private void createNewPasswordConfirm() {
        newPasswordConfirmAvailability = new JLabelModern("");
        newPasswordConfirmAvailability.setLocation(width / 2 + 220, 315);
        this.add(newPasswordConfirmAvailability);

        newPasswordConfirm = new JTextFieldModern("Confirm password");
        newPasswordConfirm.setLocation(width / 2, 315);
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
                tryNewPasswordConfirmUpdate();
            }

            @Override
            public void removeUpdate(DocumentEvent arg0) {
                tryNewPasswordConfirmUpdate();
            }

            // EFFECTS: Checks if new password confirmation is the same, if field is default then no status is shown
            private void tryNewPasswordConfirmUpdate() {
                try {
                    setRegisterClear();
                    doPasswordsMatch(newPassword.getText(), newPasswordConfirm.getText());
                    matchesHintConfirmPassword(newPassword.getText());
                    setLabel(newPasswordConfirmAvailability, "Matching!");
                } catch (RegistrationFailedException ex) {
                    setLabel(newPasswordConfirmAvailability, ex.getMessage());
                }
            }
        });
    }

    // EFFECTS: Creates register button label
    private void createRegister() {
        registerStatus = new JLabelModern("");
        registerStatus.setLocation(width / 2 + 220, 405);
        this.add(registerStatus);
        createRegisterButton();
    }

    // EFFECTS: Creates register button with functionality
    private void createRegisterButton() {
        JButton buttonRegister = new JButtonModern("REGISTER");
        buttonRegister.setBounds(width / 2, 405, 200, 40);
        buttonRegister.addActionListener(arg0 -> {
            try {
                isValidName(newName.getText());
                isValidEntry(newUsername.getText());
                udb.isUsernameFree(newUsername.getText());
                isValidEntry(newPassword.getText());
                doPasswordsMatch(newPassword.getText(), newPasswordConfirm.getText());
                matchesCheck();

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
        JLabel textHaveAccount = new JLabelModern("Already have an account?");
        textHaveAccount.setLocation(width / 2 + 220, 520);
        this.add(textHaveAccount);

        JButton buttonHaveAccount = new JButtonModern("LOGIN");
        buttonHaveAccount.setBounds(width / 2, 520, 200, 40);
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

    // EFFECTS: Clears label text
    private void clearLabel(JLabel label) {
        label.setText("");
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
        newName.setText("Name");
        newNameAvailability.setText("");
        newUsername.setText("New username");
        newUsernameAvailability.setText("");
        newPassword.setText("New password");
        newPasswordAvailability.setText("");
        newPasswordConfirm.setText("Confirm password");
        newPasswordConfirmAvailability.setText("");
    }

    // EFFECTS: Checks if string matches Name
    private void matchesHintName(String name) throws RegistrationFailedMatchesHintException {
        if (name.equals("Name")) {
            throw new RegistrationFailedMatchesHintException();
        }
    }

    // EFFECTS: Checks if string matches New username
    private void matchesHintUsername(String name) throws RegistrationFailedMatchesHintException {
        if (name.equals("New username")) {
            throw new RegistrationFailedMatchesHintException();
        }
    }

    // EFFECTS: Checks if string matches New password
    private void matchesHintPassword(String name) throws RegistrationFailedMatchesHintException {
        if (name.equals("New password")) {
            throw new RegistrationFailedMatchesHintException();
        }
    }

    // EFFECTS: Checks if string matches Confirm password
    private void matchesHintConfirmPassword(String name) throws RegistrationFailedMatchesHintException {
        if (name.equals("Confirm password")) {
            throw new RegistrationFailedMatchesHintException();
        }
    }

    // EFFECTS: Checks if all fields match hints
    private void matchesCheck() throws RegistrationFailedMatchesHintException {
        matchesHintName(newName.getText());
        matchesHintUsername(newUsername.getText());
        matchesHintPassword(newPassword.getText());
        matchesHintConfirmPassword(newPasswordConfirm.getText());
    }
}

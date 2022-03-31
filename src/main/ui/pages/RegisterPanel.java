package ui.pages;

import exceptions.RegistrationFailedException;
import exceptions.RegistrationFailedMatchesHintException;
import exceptions.RegistrationFailedMatchesHintPasswordConfirmException;
import exceptions.RegistrationFailedPasswordsDoNotMatchException;
import model.Account;
import model.UserDatabase;
import ui.modern.JButtonModern;
import ui.modern.JLabelModern;
import ui.modern.JTextFieldModern;

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
    private final int height = BankingApp.HEIGHT;
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
    }

    // ELEMENT CREATION ================================================================================================
    // Creates registration title label
    private void createTitle() {
        JLabel registrationTitle = new JLabelModern("Register for a new account!");
        registrationTitle.setBounds(width / 2 + 15, 30, 300, 40);
        registrationTitle.setFont(makeFont(23));
        this.add(registrationTitle);

        JLabel welcomeTitle = new JLabelModern("Welcome!");
        welcomeTitle.setBounds(65, 100, 200, 60);
        welcomeTitle.setFont(makeFontBold(40));
        welcomeTitle.setForeground(Color.WHITE);
        this.add(welcomeTitle);

        JLabel companyTitle = new JLabelModern("BigDecimal");
        companyTitle.setLocation(85, 300);
        companyTitle.setFont(makeFontBold(30));
        companyTitle.setForeground(Color.WHITE);
        this.add(companyTitle);

        JLabel canadaTitle = new JLabelModern("Bank of Canada");
        canadaTitle.setLocation(75, 350);
        canadaTitle.setFont(makeFontBold(25));
        canadaTitle.setForeground(Color.WHITE);
        this.add(canadaTitle);
    }

    // Creates new name text field and availability status label
    private void createNewName() {
        newNameAvailability = new JLabelModern("");
        newNameAvailability.setLocation(width / 2 + 280, 120);
        this.add(newNameAvailability);

        newName = new JTextFieldModern("Name");
        newName.setLocation(width / 2 + 60, 120);
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
        newUsernameAvailability.setLocation(width / 2 + 280, 185);
        this.add(newUsernameAvailability);

        newUsername = new JTextFieldModern("New username");
        newUsername.setLocation(width / 2 + 60, 185);
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
        newPasswordAvailability.setLocation(width / 2 + 280, 250);
        this.add(newPasswordAvailability);

        newPassword = new JTextFieldModern("New password");
        newPassword.setLocation(width / 2 + 60, 250);
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
        });
    }

    // EFFECTS: Checks if new password is available to use, if field is default then no status is shown
    private void tryNewPasswordUpdate() {
        try {
            setRegisterClear();
            isValidEntry(newPassword.getText());
            matchesHintPassword(newPassword.getText());
            setAvailable(newPasswordAvailability);

            matchesHintPasswordConfirm(newPasswordConfirm.getText());
            doPasswordsMatch(newPassword.getText(), newPasswordConfirm.getText());
            setLabel(newPasswordConfirmAvailability, "Matching!");

        } catch (RegistrationFailedPasswordsDoNotMatchException
                | RegistrationFailedMatchesHintPasswordConfirmException ex) {
            setLabel(newPasswordConfirmAvailability, ex.getMessage());
        } catch (RegistrationFailedException ex) {
            setLabel(newPasswordAvailability, ex.getMessage());
        }
    }

    // Creates new password confirmation text field and availability status label
    private void createNewPasswordConfirm() {
        newPasswordConfirmAvailability = new JLabelModern("");
        newPasswordConfirmAvailability.setLocation(width / 2 + 280, 315);
        this.add(newPasswordConfirmAvailability);

        newPasswordConfirm = new JTextFieldModern("Confirm password");
        newPasswordConfirm.setLocation(width / 2 + 60, 315);
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
                    matchesHintPasswordConfirm(newPasswordConfirm.getText());
                    doPasswordsMatch(newPassword.getText(), newPasswordConfirm.getText());
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
        registerStatus.setLocation(width / 2 + 280, 405);
        this.add(registerStatus);
        createRegisterButton();
    }

    // EFFECTS: Creates register button with functionality
    private void createRegisterButton() {
        JButton buttonRegister = new JButtonModern("REGISTER");
        buttonRegister.setBounds(width / 2 + 60, 405, 200, 40);
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
                setRegisterFailed();
                optionPane("Registration Failed! One or more fields invalid!");
            }
        });
        this.add(buttonRegister);
    }

    // EFFECTS: Creates login button and label
    //          login button switches user to login authentication panel
    private void createLogin() {
        JLabel test = new JLabelModern("Have an account?");
        test.setLocation(width / 2 - 100, 520);
        this.add(test);


        JButton buttonHaveAccount = new JButtonModern("SIGN IN");
        buttonHaveAccount.setBounds(width / 2 + 60, 520, 200, 40);
        buttonHaveAccount.addActionListener(arg0 -> {
            cl.show(container, "login");
            clearFields();
        });
        this.add(buttonHaveAccount);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(bluish);
        g.fillRect(0, 0, 330, height);
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
        newName.setForeground(Color.GRAY);
        newNameAvailability.setText("");
        newUsername.setText("New username");
        newUsername.setForeground(Color.GRAY);
        newUsernameAvailability.setText("");
        newPassword.setText("New password");
        newPassword.setForeground(Color.GRAY);
        newPasswordAvailability.setText("");
        newPasswordConfirm.setText("Confirm password");
        newPasswordConfirm.setForeground(Color.GRAY);
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
    private void matchesHintPasswordConfirm(String name) throws RegistrationFailedMatchesHintPasswordConfirmException {
        if (name.equals("Confirm password")) {
            throw new RegistrationFailedMatchesHintPasswordConfirmException();
        }
    }

    // EFFECTS: Checks if all fields match hints
    private void matchesCheck() throws RegistrationFailedMatchesHintException {
        matchesHintName(newName.getText());
        matchesHintUsername(newUsername.getText());
        matchesHintPassword(newPassword.getText());
        matchesHintPasswordConfirm(newPasswordConfirm.getText());
    }
}

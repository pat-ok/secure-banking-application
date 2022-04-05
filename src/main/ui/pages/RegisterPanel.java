package ui.pages;

import exceptions.registration.RegistrationFailedException;
import exceptions.registration.RegistrationFailedMatchesHintException;
import exceptions.registration.RegistrationFailedMatchesHintPasswordConfirmException;
import exceptions.registration.RegistrationFailedPasswordsDoNotMatchException;
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
// Child panel of container panel card layout
public class RegisterPanel extends JPanel {

    private static final int width = BankingApp.WIDTH;
    private static final int height = BankingApp.HEIGHT;
    private final UserDatabase udb;

    // Text fields and availability statuses
    private JTextField newName;
    private final String newNameHint = "Name";
    private JLabel newNameStatus;

    private JTextField newUsername;
    private final String newUsernameHint = "New username";
    private JLabel newUsernameStatus;

    private JTextField newPassword;
    private final String newPasswordHint = "New password";
    private JLabel newPasswordStatus;

    private JTextField newPasswordConfirm;
    private final String newPasswordConfirmHint = "Confirm password";
    private JLabel newPasswordConfirmStatus;

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

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(bluish);
        g.fillRect(0, 0, 330, height);
    }

    // ELEMENT CREATION ================================================================================================
    // EFFECTS: Creates registration title and bank label
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

    // NEW NAME ========================================================================================================
    // EFFECTS: Creates new name text field and availability status label
    private void createNewName() {
        newNameStatus = new JLabelModern("");
        newNameStatus.setLocation(width / 2 + 280, 120);
        this.add(newNameStatus);

        newName = new JTextFieldModern(newNameHint);
        newName.setLocation(width / 2 + 60, 120);
        this.add(newName);

        updateNewNameStatus();
    }

    // EFFECTS: Updates availability status label for new name
    private void updateNewNameStatus() {
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
                    clearRegisterStatus();
                    isValidName(newName.getText());
                    matchesHintName(newName.getText());
                    setAvailable(newNameStatus);
                } catch (RegistrationFailedException ex) {
                    newNameStatus.setText(ex.getMessage());
                }
            }
        });
    }

    // NEW USERNAME ====================================================================================================
    // EFFECTS: Creates new username text field and availability status label
    private void createNewUsername() {
        newUsernameStatus = new JLabelModern("");
        newUsernameStatus.setLocation(width / 2 + 280, 185);
        this.add(newUsernameStatus);

        newUsername = new JTextFieldModern(newUsernameHint);
        newUsername.setLocation(width / 2 + 60, 185);
        this.add(newUsername);

        updateNewUsernameStatus();
    }

    // EFFECTS: Updates availability status label for new username
    private void updateNewUsernameStatus() {
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
                    clearRegisterStatus();
                    isValidEntry(newUsername.getText());
                    udb.isUsernameFree(newUsername.getText());
                    matchesHintUsername(newUsername.getText());
                    setAvailable(newUsernameStatus);
                } catch (RegistrationFailedException ex) {
                    newUsernameStatus.setText(ex.getMessage());
                }
            }
        });
    }

    // NEW PASSWORD ====================================================================================================
    // EFFECTS: Creates new password text field and availability status label
    private void createNewPassword() {
        newPasswordStatus = new JLabelModern("");
        newPasswordStatus.setLocation(width / 2 + 280, 250);
        this.add(newPasswordStatus);

        newPassword = new JTextFieldModern(newPasswordHint);
        newPassword.setLocation(width / 2 + 60, 250);
        this.add(newPassword);

        updateNewPasswordStatus();
    }

    // EFFECTS: Updates availability status label for new password
    private void updateNewPasswordStatus() {
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
            clearRegisterStatus();
            isValidEntry(newPassword.getText());
            matchesHintPassword(newPassword.getText());
            setAvailable(newPasswordStatus);

            matchesHintPasswordConfirm(newPasswordConfirm.getText());
            doPasswordsMatch(newPassword.getText(), newPasswordConfirm.getText());
            newPasswordConfirmStatus.setText("Matching!");

        } catch (RegistrationFailedPasswordsDoNotMatchException
                | RegistrationFailedMatchesHintPasswordConfirmException ex) {
            newPasswordConfirmStatus.setText(ex.getMessage());
        } catch (RegistrationFailedException ex) {
            newPasswordStatus.setText(ex.getMessage());
        }
    }

    // NEW PASSWORD CONFIRMATION =======================================================================================
    // EFFECTS: Creates new password confirmation text field and availability status label
    private void createNewPasswordConfirm() {
        newPasswordConfirmStatus = new JLabelModern("");
        newPasswordConfirmStatus.setLocation(width / 2 + 280, 315);
        this.add(newPasswordConfirmStatus);

        newPasswordConfirm = new JTextFieldModern(newPasswordConfirmHint);
        newPasswordConfirm.setLocation(width / 2 + 60, 315);
        this.add(newPasswordConfirm);

        updateNewPasswordConfirmStatus();
    }

    // EFFECTS: Updates availability status label for new password confirmation
    private void updateNewPasswordConfirmStatus() {
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
                    clearRegisterStatus();
                    matchesHintPasswordConfirm(newPasswordConfirm.getText());
                    doPasswordsMatch(newPassword.getText(), newPasswordConfirm.getText());
                    newPasswordConfirmStatus.setText("Matching!");
                } catch (RegistrationFailedException ex) {
                    newPasswordConfirmStatus.setText(ex.getMessage());
                }
            }
        });
    }

    // REGISTER BUTTON =================================================================================================
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
                // all fields valid and are not default prompts
                isValidName(newName.getText());
                isValidEntry(newUsername.getText());
                udb.isUsernameFree(newUsername.getText());
                isValidEntry(newPassword.getText());
                doPasswordsMatch(newPassword.getText(), newPasswordConfirm.getText());
                confirmNoFieldsMatchHints();

                // create new account and trigger confirmation pop-up
                Account acc = new Account(createPassword(newPassword.getText()), capitalizeName(newName.getText()));
                udb.storeAccount(newUsername.getText(), acc);
                clearFields();
                registerStatus.setText("Account registered!");
                warningPane("Registration Success!");
            } catch (RegistrationFailedException ex) {
                registerStatus.setText("Registration Failed!");
                warningPane("Registration Failed! One or more fields invalid!");
            }
        });
        this.add(buttonRegister);
    }

    // LOGIN BUTTON ====================================================================================================
    // EFFECTS: Creates login button and label
    //          login button switches user to login authentication panel
    private void createLogin() {
        JLabel test = new JLabelModern("Have an account?");
        test.setLocation(width / 2 - 100, 520);
        this.add(test);

        JButton buttonHaveAccount = new JButtonModern("SIGN IN");
        buttonHaveAccount.setBounds(width / 2 + 60, 520, 200, 40);
        buttonHaveAccount.addActionListener(arg0 -> {
            cl.show(container, "login page");
            clearFields();
        });
        this.add(buttonHaveAccount);
    }

    // HELPER METHODS ==================================================================================================
    // EFFECTS: creates a new salted and hashed password from a clear text password
    private String createPassword(String newPassword) {
        String salt = salt();
        return salt + hashFunction(salt + newPassword);
    }

    // EFFECTS: Sets label to available
    private void setAvailable(JLabel label) {
        label.setText("Available!");
    }

    // EFFECTS: Clears registration status
    private void clearRegisterStatus() {
        registerStatus.setText("");
    }

    // EFFECTS: Clears user input from all fields and sets to default prompts
    private void clearFields() {
        newName.setText(newNameHint);
        newName.setForeground(Color.GRAY);
        newNameStatus.setText("");
        newUsername.setText(newUsernameHint);
        newUsername.setForeground(Color.GRAY);
        newUsernameStatus.setText("");
        newPassword.setText(newPasswordHint);
        newPassword.setForeground(Color.GRAY);
        newPasswordStatus.setText("");
        newPasswordConfirm.setText(newPasswordConfirmHint);
        newPasswordConfirm.setForeground(Color.GRAY);
        newPasswordConfirmStatus.setText("");
    }

    // EFFECTS: Checks if string matches name hint
    private void matchesHintName(String name) throws RegistrationFailedMatchesHintException {
        if (name.equals(newNameHint)) {
            throw new RegistrationFailedMatchesHintException();
        }
    }

    // EFFECTS: Checks if string matches new username hint
    private void matchesHintUsername(String name) throws RegistrationFailedMatchesHintException {
        if (name.equals(newUsernameHint)) {
            throw new RegistrationFailedMatchesHintException();
        }
    }

    // EFFECTS: Checks if string matches new password hint
    private void matchesHintPassword(String name) throws RegistrationFailedMatchesHintException {
        if (name.equals(newPasswordHint)) {
            throw new RegistrationFailedMatchesHintException();
        }
    }

    // EFFECTS: Checks if string matches password confirmation hint
    private void matchesHintPasswordConfirm(String name) throws RegistrationFailedMatchesHintPasswordConfirmException {
        if (name.equals(newPasswordConfirmHint)) {
            throw new RegistrationFailedMatchesHintPasswordConfirmException();
        }
    }

    // EFFECTS: Confirms that no fields match hints
    private void confirmNoFieldsMatchHints() throws RegistrationFailedMatchesHintException {
        matchesHintName(newName.getText());
        matchesHintUsername(newUsername.getText());
        matchesHintPassword(newPassword.getText());
        matchesHintPasswordConfirm(newPasswordConfirm.getText());
    }
}

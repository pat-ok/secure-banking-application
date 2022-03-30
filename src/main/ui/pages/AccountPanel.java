package ui.pages;

import exceptions.*;
import model.Account;
import model.UserDatabase;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.util.Map;

import static model.Formatting.hasSufficientFunds;
import static model.Formatting.isValidAmount;
import static ui.pages.BankingApp.*;

// Represents account UI after account login authentication
// Child panel of LoginPanel card layout
public class AccountPanel extends JPanel {

    private final UserDatabase udb;
    private final Account account;

    private JLabel balanceLabelActual;
    private JTextArea notificationsPanel;

    // user choice text
    private final String depositText = "Deposit Amount:";
    private final String withdrawText = "Withdraw Amount:";
    private final String transferRecipientText = "Recipient Username:";
    private final String transferText = "Transfer Amount:";
    private final String dollarSign = "$";

    // admin choice text
    private final String lockText = "Account to lock";
    private final String unlockText = "Account to unlock";
    private final String viewText = "Account to view:";

    // choice fields
    private JLabel labelOne;
    private JTextField fieldOne;
    private JLabel labelTwo;
    private JLabel labelTwoDollar;
    private JTextField fieldTwo;
    private JButton confirmButton;

    // choice buttons
    private JButton buttonOne;
    private JButton buttonTwo;
    private JButton buttonThree;
    private JButton buttonFour;

    // Constructor for account panel
    public AccountPanel(UserDatabase udb, Account account, Boolean admin) {
        this.setLayout(null);
        this.udb = udb;
        this.account = account;
        this.setBackground(Color.MAGENTA);

        addFields();

        if (admin) {
            setAdmin();
        } else {
            setUser();
        }
        this.setVisible(true);
    }

    // EFFECTS: adds all fields to the panel
    private void addFields() {
        // status fields
        createWelcomeLabel();
        createBalanceLabel();
        createBalanceLabelActual();
        createNotificationsPanel();

        // choice buttons
        createChoiceButtons();

        // choice fields
        createSetOne();
        createSetTwo();
        createConfirmButton();

        // logout button
        createLogoutButton();
    }

    // STATUS FIELDS ===================================================================================================
    // EFFECTS: creates label with welcome text
    private void createWelcomeLabel() {
        // status fields
        JLabel welcomeLabel = new JLabel("Welcome back, " + account.getName() + "!");
        welcomeLabel.setBounds(50, 50, 800, 40);
        welcomeLabel.setFont(makeFont(30));
        this.add(welcomeLabel);
    }

    // EFFECTS: creates label with balance text
    private void createBalanceLabel() {
        JLabel balanceLabel = new JLabel("Current Balance: ");
        balanceLabel.setBounds(50, 120, 400, 40);
        balanceLabel.setFont(makeFont(25));
        this.add(balanceLabel);
    }

    // EFFECTS: creates label with actual balance
    private void createBalanceLabelActual() {
        balanceLabelActual = new JLabel(account.getBalanceString());
        balanceLabelActual.setBounds(250, 120, 400, 40);
        balanceLabelActual.setFont(makeFont(25));
        this.add(balanceLabelActual);
    }

    // EFFECTS: creates label with notifications
    private void createNotificationsPanel() {
        notificationsPanel = new JTextArea();
        notificationsPanel.setEditable(false);
        notificationsPanel.setFont(makeFont(16));
        notificationsPanel.setLineWrap(true);
        notificationsPanel.setWrapStyleWord(true);
        JScrollPane notifications = new JScrollPane(notificationsPanel,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        notifications.setAutoscrolls(true);
        notifications.setBounds(50, 200, 500, 200);
        this.add(notifications);
    }

    // CHOICE BUTTONS ==================================================================================================
    // EFFECTS: creates four buttons
    private void createChoiceButtons() {
        buttonOne = new JButton();
        int buttonWidth = 110;
        int buttonHeight = 40;
        buttonOne.setBounds(50, 440, buttonWidth, buttonHeight);
        this.add(buttonOne);

        buttonTwo = new JButton();
        buttonTwo.setBounds(180, 440, buttonWidth, buttonHeight);
        this.add(buttonTwo);

        buttonThree = new JButton();
        buttonThree.setBounds(310, 440, buttonWidth, buttonHeight);
        this.add(buttonThree);

        buttonFour = new JButton();
        buttonFour.setBounds(440, 440, buttonWidth, buttonHeight);
        this.add(buttonFour);
    }

    // CHOICE FIELDS ===================================================================================================
    // EFFECTS: Creates first set of label and text field, primarily used for entering account usernames
    private void createSetOne() {
        labelOne = new JLabel("");
        labelOne.setBounds(700, 120, 200, 40);
        labelOne.setFont(makeFont(17));
        this.add(labelOne);

        fieldOne = new JTextField();
        fieldOne.setBounds(700, 160, 200, 40);
        fieldOne.setFont(makeFont(25));
        this.add(fieldOne);
        fieldOne.setVisible(false);
    }

    // EFFECTS: Creates second set of labels and text field, primarily used for entering amounts
    private void createSetTwo() {
        labelTwo = new JLabel("");
        labelTwo.setBounds(700, 210, 230, 40);
        labelTwo.setFont(makeFont(17));
        this.add(labelTwo);

        labelTwoDollar = new JLabel("");
        labelTwoDollar.setBounds(670, 250, 200, 40);
        labelTwoDollar.setFont(makeFont(25));
        this.add(labelTwoDollar);

        fieldTwo = new JTextField();
        fieldTwo.setBounds(700, 250, 200, 40);
        fieldTwo.setFont(makeFont(25));
        this.add(fieldTwo);
        fieldTwo.setVisible(false);
    }

    // EFFECTS: Creates a confirmation button
    private void createConfirmButton() {
        confirmButton = new JButton("Confirm");
        confirmButton.setBounds(700, 320, 110, 40);
        this.add(confirmButton);
        confirmButton.setVisible(false);
    }

    // LOGOUT BUTTON ===================================================================================================
    // EFFECTS: Creates a logout button
    private void createLogoutButton() {
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(50, 535, 110, 40);
        logoutButton.addActionListener(arg0 -> {
            AccountPanel.this.setVisible(false);
            cl.show(container, "login");
            revalidate();
            repaint();
        });
        this.add(logoutButton);
    }

    // ADMIN ACCOUNT ===================================================================================================
    // EFFECTS: Sets status and choice buttons to admin options, including
    //          lock accounts,
    //          unlock accounts,
    //          view accounts,
    //          view user database
    private void setAdmin() {
        balanceLabelActual.setText("N/A");
        notificationsPanel.setText("Admin Access: GRANTED\n\n"
                + "Please select from the following options:\n\n"
                + "[1] Lock a registered account to prevent access\n"
                + "[2] Unlock a registered account to regain access\n"
                + "[3] View a registered account's information\n"
                + "[4] View all users in the database");

        setAdminLockButton();
        setAdminUnlockButton();
        setAdminViewButton();
        setAdminDatabaseButton();
        setAdminConfirmButton();
    }

    // EFFECTS: Sets first button to trigger fields to change for user input to lock accounts
    private void setAdminLockButton() {
        buttonOne.setText("Lock");
        buttonOne.addActionListener(arg0 -> {
            clearFields();
            labelOne.setText(lockText);
            fieldOne.setVisible(true);
            labelTwo.setText("Enter 'confirm' to proceed");
            fieldTwo.setVisible(true);
            confirmButton.setVisible(true);
        });
    }

    // EFFECTS: Sets second button to trigger fields to change for user input to unlock accounts
    private void setAdminUnlockButton() {
        buttonTwo.setText("Unlock");
        buttonTwo.addActionListener(arg0 -> {
            clearFields();
            labelOne.setText(unlockText);
            fieldOne.setVisible(true);
            labelTwo.setText("Enter 'confirm' to proceed");
            fieldTwo.setVisible(true);
            confirmButton.setVisible(true);
        });
    }

    // EFFECTS: Sets third button to trigger fields to change for user input to view accounts
    private void setAdminViewButton() {
        buttonThree.setText("View");
        buttonThree.addActionListener(arg0 -> {
            clearFields();
            labelOne.setText(viewText);
            fieldOne.setVisible(true);
            confirmButton.setVisible(true);
        });
    }

    // EFFECTS: Sets fourth button to trigger pop up showing account database
    private void setAdminDatabaseButton() {
        buttonFour.setText("Database");
        buttonFour.addActionListener(arg0 -> {
            clearFields();
            StringBuilder accountList = new StringBuilder();
            for (Map.Entry<String, Account> entry : udb.getUserDatabase().entrySet()) {
                String k = entry.getKey();
                Account v = entry.getValue();
                accountList.append("Username: ").append(k).append("  Password: ").append(v.getPassword()).append("\n");
            }
            createPopFrame("Account Database",
                    "Accounts stored in database:\n\n" + accountList);
        });
    }

    // EFFECTS: Sets confirm button to execute appropriate action, including
    //          lock account,
    //          unlock account,
    //          view account
    private void setAdminConfirmButton() {
        confirmButton.addActionListener(arg0 -> {
            switch (labelOne.getText()) {
                case lockText:
                    actionLock();
                    break;
                case unlockText:
                    actionUnlock();
                    break;
                default:
                    actionView();
            }
        });
    }

    // EFFECTS: Locks an account
    private void actionLock() {
        try {
            udb.authUsername(fieldOne.getText());
            confirmationTrue(fieldTwo.getText());
            udb.getUserDatabase().get(fieldOne.getText()).lockAccount();
            optionPane(fieldOne.getText() + " has been locked!");
        } catch (AuthenticationFailedUsernameException | UnconfirmedException ex) {
            optionPane(ex.getMessage());
        }
    }

    // EFFECTS: Unlocks an account
    private void actionUnlock() {
        try {
            udb.authUsername(fieldOne.getText());
            confirmationTrue(fieldTwo.getText());
            udb.getUserDatabase().get(fieldOne.getText()).unlockAccount();
            optionPane(fieldOne.getText() + " has been unlocked!");
        } catch (AuthenticationFailedUsernameException | UnconfirmedException ex) {
            optionPane(ex.getMessage());
        }
    }

    // EFFECTS: Views details of an account
    private void actionView() {
        try {
            udb.authUsername(fieldOne.getText());
            Account user = udb.getUserDatabase().get(fieldOne.getText());
            String content = "Account: " + fieldOne.getText()
                    + "\n\nLocked status: " + user.getLock()
                    + "\nName: " + user.getName()
                    + "\nBalance: " + user.getBalanceString()
                    + "\nNotifications: " + user.getNotificationsString()
                    + "\nTransactions: " + user.transactionHistory();
            createPopFrame("Account Information", content);
        } catch (AuthenticationFailedUsernameException ex) {
            optionPane(ex.getMessage());
        }
    }

    // USER ACCOUNT ====================================================================================================
    // EFFECTS: Sets status and choice buttons to user options, including
    //          deposit money,
    //          withdraw money,
    //          transfer money,
    //          view transaction history
    private void setUser() {
        balanceLabelActual.setText(account.getBalanceString());
        notificationsPanel.setText(account.loginNotifications());

        setUserDepositButton();
        setUserWithdrawButton();
        setUserTransferButton();
        setUserHistoryButton();
        setUserConfirmButton();
    }

    // EFFECTS: Sets first button to trigger fields to change for user input to deposit money
    private void setUserDepositButton() {
        buttonOne.setText("Deposit");
        buttonOne.addActionListener(arg0 -> {
            clearFields();
            labelTwo.setText(depositText);
            labelTwoDollar.setText(dollarSign);
            fieldTwo.setVisible(true);
            confirmButton.setVisible(true);
            notificationsPanel.setVisible(false);
        });
    }

    // EFFECTS: Sets second button to trigger fields to change for user input to withdraw money
    private void setUserWithdrawButton() {
        buttonTwo.setText("Withdraw");
        buttonTwo.addActionListener(arg0 -> {
            clearFields();
            labelTwo.setText(withdrawText);
            labelTwoDollar.setText(dollarSign);
            fieldTwo.setVisible(true);
            confirmButton.setVisible(true);
            notificationsPanel.setVisible(true);
        });
    }

    // EFFECTS: Sets third button to trigger fields to change for user input to transfer money
    private void setUserTransferButton() {
        buttonThree.setText("Transfer");
        buttonThree.addActionListener(arg0 -> {
            clearFields();
            labelOne.setText(transferRecipientText);
            labelTwo.setText(transferText);
            labelTwoDollar.setText(dollarSign);
            fieldOne.setVisible(true);
            fieldTwo.setVisible(true);
            confirmButton.setVisible(true);
        });
    }

    // EFFECTS: Sets fourth button to trigger pop up displaying transaction history
    private void setUserHistoryButton() {
        buttonFour.setText("History");
        buttonFour.addActionListener(arg0 -> {
            clearFields();
            createPopFrame("Transaction History", account.transactionHistory());
        });
    }

    // EFFECTS: Sets confirm button to execute appropriate action, including
    //          deposit money,
    //          withdraw money,
    //          transfer money
    private void setUserConfirmButton() {
        confirmButton.addActionListener(arg0 -> {
            switch (labelTwo.getText()) {
                case depositText:
                    actionDeposit();
                    break;
                case withdrawText:
                    actionWithdraw();
                    break;
                default:
                    actionTransfer();
            }
        });
    }

    // EFFECTS: Deposits money in user account and updates actual balance text
    private void actionDeposit() {
        try {
            isValidAmount(fieldTwo.getText());
            optionPane(account.deposit(fieldTwo.getText()));
            balanceLabelActual.setText(account.getBalanceString());
            clearFields();
        } catch (AmountFailedInvalidEntryException ex) {
            optionPane(ex.getMessage());
        }
    }

    // EFFECTS: Withdraws money from user account and updates actual balance text
    private void actionWithdraw() {
        try {
            isValidAmount(fieldTwo.getText());
            hasSufficientFunds(new BigDecimal(fieldTwo.getText()), account.getBalance());
            optionPane(account.withdraw(fieldTwo.getText()));
            balanceLabelActual.setText(account.getBalanceString());
            clearFields();
        } catch (AmountFailedException ex) {
            optionPane(ex.getMessage());
        }
    }

    // EFFECTS: Transfers money from user account to recipient account and updates actual balance text
    private void actionTransfer() {
        try {
            udb.authUsername(fieldOne.getText());
            isValidAmount(fieldTwo.getText());
            hasSufficientFunds(new BigDecimal(fieldTwo.getText()), account.getBalance());
            Account recipientAccount = udb.getUserDatabase().get(fieldOne.getText());
            doTransferFromTo(fieldTwo.getText(), account, recipientAccount);
            optionPane(recipientAccount.getName() + " has received your Interac eTransfer!");
            balanceLabelActual.setText(account.getBalanceString());
            clearFields();
        } catch (AuthenticationFailedUsernameException | AmountFailedException ex) {
            optionPane(ex.getMessage());
        }
    }

    // HELPER METHODS ==================================================================================================
    // EFFECTS: Clears all choice fields and sets text fields to non-visible
    private void clearFields() {
        labelOne.setText("");
        fieldOne.setText("");
        fieldOne.setVisible(false);
        labelTwo.setText("");
        labelTwoDollar.setText("");
        fieldTwo.setText("");
        fieldTwo.setVisible(false);
        confirmButton.setVisible(false);
    }

    // EFFECTS: Creates pop-up JFrame with a title and scrollable content
    private void createPopFrame(String title, String content) {
        JFrame frame = new JFrame(title);
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTextArea text = new JTextArea(content);
        text.setEditable(false);
        text.setFont(makeFont(16));
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
        JScrollPane scroll =
                new JScrollPane(text, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setAutoscrolls(true);
        scroll.setBounds(50, 200, 500, 200);
        frame.add(scroll);

        frame.setVisible(true);
    }

    // EFFECTS: From sender:
    //          subtracts amount from sender balance,
    //          adds eTransfer to transaction history,
    //          To recipient:
    //          adds amount to recipient balance,
    //          adds eTransfer to transaction history,
    //          adds eTransfer to notifications list
    private void doTransferFromTo(String amount, Account sender, Account recipient) {
        sender.transferOut(amount, recipient.getName());
        recipient.transferIn(amount, sender.getName());
    }

    // EFFECTS: checks whether confirmation string is "confirm"
    private void confirmationTrue(String confirmation) throws UnconfirmedException {
        if (!confirmation.equals("confirm")) {
            throw new UnconfirmedException();
        }
    }
}

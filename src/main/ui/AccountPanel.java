package ui;

import exceptions.InsufficientFundsException;
import exceptions.InvalidAmountException;
import exceptions.UnconfirmedException;
import exceptions.UsernameNotFoundException;
import model.Account;
import model.UserDatabase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

import static model.Formatting.hasSufficientFunds;
import static model.Formatting.isValidAmount;
import static ui.BankingApp.*;

public class AccountPanel extends JPanel {

    private UserDatabase udb;
    //private HashMap<String, Account> udbInfo = udb.getUserDatabase();
    private Account account;

    // status fields
    private JLabel welcomeLabel;
    private JLabel balanceLabel;
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

    private JButton logoutButton;





    private void createWelcomeLabel() {
        welcomeLabel = new JLabel("Welcome back, " + account.getName() + "!");
        welcomeLabel.setBounds(50, 50, 800, 40);
        welcomeLabel.setFont(makeFont(30));
        this.add(welcomeLabel);
    }

    private void createBalanceLabel() {
        balanceLabel = new JLabel("Current Balance: ");
        balanceLabel.setBounds(50, 120, 400, 40);
        balanceLabel.setFont(makeFont(25));
        this.add(balanceLabel);
    }

    private void createBalanceLabelActual() {
        balanceLabelActual = new JLabel(account.getBalanceString());
        balanceLabelActual.setBounds(250, 120, 400, 40);
        balanceLabelActual.setFont(makeFont(25));
        this.add(balanceLabelActual);
    }

    private void createNotificationsPanel() {
        notificationsPanel = new JTextArea();
        notificationsPanel.setEditable(false);
        notificationsPanel.setFont(makeFont(16));
        notificationsPanel.setLineWrap(true);
        JScrollPane notifications = new JScrollPane(notificationsPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        notifications.setAutoscrolls(true);
        notifications.setBounds(50, 200, 500, 200);
        this.add(notifications);
    }

    private void createChoiceButtons() {
        buttonOne = new JButton();
        buttonOne.setBounds(50, 440, 110, 40);
        this.add(buttonOne);

        buttonTwo = new JButton();
        buttonTwo.setBounds(180, 440, 110, 40);
        this.add(buttonTwo);

        buttonThree = new JButton();
        buttonThree.setBounds(310, 440, 110, 40);
        this.add(buttonThree);

        buttonFour = new JButton();
        buttonFour.setBounds(440, 440, 110, 40);
        this.add(buttonFour);
    }

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

        confirmButton = new JButton("Confirm");
        confirmButton.setBounds(700, 320, 110, 40);
        this.add(confirmButton);
        confirmButton.setVisible(false);
    }

    private void setUserConfirmButton() {
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
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
            }
        });
    }

    private void setAdminConfirmButton() {
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
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
            }
        });
    }

    private void createLogoutButton() {
        logoutButton = new JButton("Logout");
        logoutButton.setBounds(50, 535, 110, 40);
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                AccountPanel.this.setVisible(false);
                cl.show(container, "login");
                revalidate();
                repaint();
            }
        });
        this.add(logoutButton);
    }

    private void clearChoices() {
        labelOne.setText("");
        fieldOne.setText("");
        fieldOne.setVisible(false);
        labelTwo.setText("");
        labelTwoDollar.setText("");
        fieldTwo.setText("");
        fieldTwo.setVisible(false);
        confirmButton.setVisible(false);
    }

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

        // logout button
        createLogoutButton();
    }

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

    private void setAdminLockButton() {
        buttonOne.setText("Lock");
        buttonOne.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                clearChoices();
                labelOne.setText(lockText);
                fieldOne.setVisible(true);
                labelTwo.setText("Enter 'CONFIRM' to proceed");
                fieldTwo.setVisible(true);
                confirmButton.setVisible(true);
            }
        });
    }

    private void setAdminUnlockButton() {
        buttonTwo.setText("Unlock");
        buttonTwo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                clearChoices();
                labelOne.setText(unlockText);
                fieldOne.setVisible(true);
                labelTwo.setText("Enter 'CONFIRM' to proceed");
                fieldTwo.setVisible(true);
                confirmButton.setVisible(true);
            }
        });
    }

    private void setAdminViewButton() {
        buttonThree.setText("View");
        buttonThree.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                clearChoices();
                labelOne.setText(viewText);
                fieldOne.setVisible(true);
                confirmButton.setVisible(true);
            }
        });
    }

    private void setAdminDatabaseButton() {
        buttonFour.setText("Database");
        buttonFour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                clearChoices();
                StringBuilder accountList = new StringBuilder();
                udb.getUserDatabase().forEach((k, v) -> accountList.append("Username: " + k + "  Password: " + v.getPassword() + "\n"));
                createPopFrame("Account Database", "Accounts stored in database:\n\n" + String.valueOf(accountList));
            }
        });
    }

    private void setUser() {
        balanceLabelActual.setText(account.getBalanceString());
        notificationsPanel.setText(account.loginNotifications());

        setUserDepositButton();
        setUserWithdrawButton();
        setUserTransferButton();
        setUserHistoryButton();

        setUserConfirmButton();
    }

    private void setUserDepositButton() {
        buttonOne.setText("Deposit");
        buttonOne.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                clearChoices();
                labelTwo.setText(depositText);
                labelTwoDollar.setText(dollarSign);
                fieldTwo.setVisible(true);
                confirmButton.setVisible(true);
            }
        });
    }

    private void setUserWithdrawButton() {
        buttonTwo.setText("Withdraw");
        buttonTwo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                clearChoices();
                labelTwo.setText(withdrawText);
                labelTwoDollar.setText(dollarSign);
                fieldTwo.setVisible(true);
                confirmButton.setVisible(true);
            }
        });
    }

    private void setUserTransferButton() {
        buttonThree.setText("Transfer");
        buttonThree.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                clearChoices();
                labelOne.setText(transferRecipientText);
                labelTwo.setText(transferText);
                labelTwoDollar.setText(dollarSign);
                fieldOne.setVisible(true);
                fieldTwo.setVisible(true);
                confirmButton.setVisible(true);
            }
        });
    }

    private void setUserHistoryButton() {
        buttonFour.setText("History");
        buttonFour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                clearChoices();
                createPopFrame("Transaction History", account.transactionHistory());
            }
        });
    }

    private void createPopFrame(String title, String content) {
        JFrame frame = new JFrame(title);
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTextArea text = new JTextArea(content);
        text.setEditable(false);
        text.setFont(makeFont(16));
        text.setLineWrap(true);
        JScrollPane scroll = new JScrollPane(text, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setAutoscrolls(true);
        scroll.setBounds(50, 200, 500, 200);
        frame.add(scroll);

        frame.setVisible(true);
    }



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

    private void actionDeposit() {
        try {
            isValidAmount(fieldTwo.getText());
            JOptionPane.showMessageDialog(null, account.deposit(fieldTwo.getText()), "Banking Application", JOptionPane.WARNING_MESSAGE);
            balanceLabelActual.setText(account.getBalanceString());
            clearChoices();
        } catch (InvalidAmountException iae) {
            JOptionPane.showMessageDialog(null, "Invalid Amount!", "Banking Application", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void actionWithdraw() {
        try {
            isValidAmount(fieldTwo.getText());
            hasSufficientFunds(new BigDecimal(fieldTwo.getText()), account.getBalance());
            JOptionPane.showMessageDialog(null, account.withdraw(fieldTwo.getText()), "Banking Application", JOptionPane.WARNING_MESSAGE);
            balanceLabelActual.setText(account.getBalanceString());
            clearChoices();
        } catch (InvalidAmountException iae) {
            JOptionPane.showMessageDialog(null, "Invalid Amount!", "Banking Application", JOptionPane.WARNING_MESSAGE);
        } catch (InsufficientFundsException ife) {
            JOptionPane.showMessageDialog(null, "Insufficient Funds!", "Banking Application", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void actionTransfer() {
        try {
            udb.authUsername(fieldOne.getText());
            isValidAmount(fieldTwo.getText());
            hasSufficientFunds(new BigDecimal(fieldTwo.getText()), account.getBalance());
            Account recipientAccount = udb.getUserDatabase().get(fieldOne.getText());
            doTransferFromTo(fieldTwo.getText(), account, recipientAccount);
            JOptionPane.showMessageDialog(null, recipientAccount.getName() + " has received your Interac eTransfer!", "Banking Application", JOptionPane.WARNING_MESSAGE);
            balanceLabelActual.setText(account.getBalanceString());
            clearChoices();
        } catch (UsernameNotFoundException unfe) {
            JOptionPane.showMessageDialog(null, "User not found!", "Banking Application", JOptionPane.WARNING_MESSAGE);
        } catch (InvalidAmountException iae) {
            JOptionPane.showMessageDialog(null, "Invalid Amount!", "Banking Application", JOptionPane.WARNING_MESSAGE);
        } catch (InsufficientFundsException ife) {
            JOptionPane.showMessageDialog(null, "Insufficient Funds!", "Banking Application", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void actionLock() {
        try {
            udb.authUsername(fieldOne.getText());
            confirmationTrue(fieldTwo.getText());
            udb.getUserDatabase().get(fieldOne.getText()).lockAccount();
            JOptionPane.showMessageDialog(null, fieldOne.getText() + " has been locked!", "Banking Application", JOptionPane.WARNING_MESSAGE);
        } catch (UnconfirmedException ue) {
            JOptionPane.showMessageDialog(null, "Please confirm!", "Banking Application", JOptionPane.WARNING_MESSAGE);
        } catch (UsernameNotFoundException unfe) {
            JOptionPane.showMessageDialog(null, "Username not found!", "Banking Application", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void actionUnlock() {
        try {
            udb.authUsername(fieldOne.getText());
            confirmationTrue(fieldTwo.getText());
            udb.getUserDatabase().get(fieldOne.getText()).unlockAccount();
            JOptionPane.showMessageDialog(null, fieldOne.getText() + " has been unlocked!", "Banking Application", JOptionPane.WARNING_MESSAGE);
        } catch (UnconfirmedException ue) {
            JOptionPane.showMessageDialog(null, "Please confirm!", "Banking Application", JOptionPane.WARNING_MESSAGE);
        } catch (UsernameNotFoundException unfe) {
            JOptionPane.showMessageDialog(null, "Username not found!", "Banking Application", JOptionPane.WARNING_MESSAGE);
        }
    }

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
        } catch (UsernameNotFoundException unfe) {
            JOptionPane.showMessageDialog(null, "Username not found!", "Banking Application", JOptionPane.WARNING_MESSAGE);
        }
    }

    // REQUIRES: nothing
    // MODIFIES: nothing
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

    private void confirmationTrue(String confirmation) throws UnconfirmedException {
        if (!confirmation.equals("CONFIRM")) {
            throw new UnconfirmedException();
        }
    }
}

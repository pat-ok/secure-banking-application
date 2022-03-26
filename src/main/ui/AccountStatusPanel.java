package ui;

import model.Account;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static ui.BankingApp.makeFont;

public class AccountStatusPanel extends JPanel {

    private Account account;

    public AccountStatusPanel(Account account) {
        super.setLayout(null);
        super.setBounds(0, 0, 600, 650);
        super.setBackground(Color.lightGray);

        this.account = account;

        // adding title
        JLabel accountStatusTitle = new JLabel("Welcome to BigDecimal Bank of Canada!"
                + "\nWhere bits don't round your coins!");
        accountStatusTitle.setBounds(250, 130, 300, 80);
        accountStatusTitle.setFont(makeFont(23));
        super.add(accountStatusTitle);

        //addTest();

        JLabel test = new JLabel(account.getBalanceString());
        test.setBounds(80, 180, 100, 35);
        this.add(test);

        JButton testButton = new JButton("test");
        testButton.setBounds(10, 10, 100, 35);
        testButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                addTest();
                AccountStatusPanel.super.setVisible(true);
                revalidate();
                repaint();
            }
        });
        super.add(testButton);
    }

    private void addTest() {
        JLabel test = new JLabel(account.getBalanceString());
        test.setBounds(80, 280, 100, 35);
        this.add(test);
        this.setVisible(true);

    }
}

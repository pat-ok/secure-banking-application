package ui;

import javax.swing.*;
import java.awt.*;

import static ui.BankingApp.makeFont;

public class AccountStatusPanel extends JPanel {

    public AccountStatusPanel() {
        super.setLayout(null);
        super.setBounds(0, 0, 600, 650);
        super.setBackground(Color.lightGray);

        // adding title
        JLabel accountStatusTitle = new JLabel("Welcome to BigDecimal Bank of Canada!"
                + "\nWhere bits don't round your coins!");
        accountStatusTitle.setBounds(250, 130, 300, 80);
        accountStatusTitle.setFont(makeFont(23));
        super.add(accountStatusTitle);
    }
}

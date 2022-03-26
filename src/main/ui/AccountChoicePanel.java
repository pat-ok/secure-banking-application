package ui;

import javax.swing.*;
import java.awt.*;

import static ui.BankingApp.makeFont;

public class AccountChoicePanel extends JPanel {

    public AccountChoicePanel() {
        super.setLayout(null);
        super.setBounds(600, 0, 400, 650);
        super.setBackground(Color.CYAN);

        // adding title
        JLabel accountStatusTitle = new JLabel("This is the choice panel");
        accountStatusTitle.setBounds(250, 130, 300, 80);
        accountStatusTitle.setFont(makeFont(23));
        super.add(accountStatusTitle);
    }
}

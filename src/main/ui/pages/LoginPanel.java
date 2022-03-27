package ui.pages;

import model.UserDatabase;

import javax.swing.*;
import java.awt.*;

// Represents a card layout container holding authentication panel and account panel
// Child panel of container panel from BankingApp
public class LoginPanel extends JPanel {

    protected static CardLayout lcl;

    public LoginPanel(UserDatabase udb) {
        // parent container (this)
        lcl = new CardLayout();
        this.setLayout(lcl);

        // child login panel
        JPanel loginPanel = new LoginPanelAuthentication(udb, this);
        this.add(loginPanel, "login");

        // card layout setup
        lcl.show(this, "login");
    }
}

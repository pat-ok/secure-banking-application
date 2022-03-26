package ui.pages;

import model.UserDatabase;

import javax.swing.*;
import java.awt.*;

// Represents a container holding authentication panel and account panel
public class LoginPanel extends JPanel {

    protected static CardLayout lcl;

    public LoginPanel(UserDatabase udb) {
        // parent container (this)
        lcl = new CardLayout();
        super.setLayout(lcl);

        // child login panel
        JPanel loginPanel = new LoginPanelAuthentication(udb, this);
        super.add(loginPanel, "login");

        // card layout setup
        lcl.show(this, "login");
    }
}

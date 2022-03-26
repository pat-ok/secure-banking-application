package ui.pages;

import model.UserDatabase;

import javax.swing.*;
import java.awt.*;

// Represents a container holding
public class LoginPanel extends JPanel {

    protected static CardLayout lcl;

    public LoginPanel(UserDatabase udb) {
        // parent container (this)
        lcl = new CardLayout();
        super.setLayout(lcl);

        // child login panel
        JPanel loginPanel = new ui.pages.LoginPanelAuthentication(udb, this);
        super.add(loginPanel, "login");

        // child JSplit panel

        // card layout setup
        lcl.show(this, "login");
    }
}

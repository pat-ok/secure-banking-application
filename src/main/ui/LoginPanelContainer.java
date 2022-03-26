package ui;

import model.UserDatabase;

import javax.swing.*;
import java.awt.*;

public class LoginPanelContainer extends JPanel {

    protected static CardLayout lcl;

    public LoginPanelContainer(UserDatabase udb) {
        // parent container (this)
        lcl = new CardLayout();
        super.setLayout(lcl);

        // child login panel
        JPanel loginPanel = new LoginPanel(udb, this);
        super.add(loginPanel, "login");

        // child JSplit panel

        // card layout setup
        lcl.show(this, "login");
    }
}

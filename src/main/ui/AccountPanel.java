package ui;

import model.Account;

import javax.swing.*;

public class AccountPanel extends JSplitPane {

    protected JPanel accountStatus;
    protected JPanel accountChoice;

    public AccountPanel(Account account, JPanel left, JPanel right) {
        super(JSplitPane.HORIZONTAL_SPLIT, left, right);
        super.setLayout(null);

    }
}

package ui.modern;

import javax.swing.*;

import static ui.pages.BankingApp.makeFont;

// Represents a modernized JTextArea
public class JTextAreaModern extends JTextArea {
    public JTextAreaModern(String msg) {
        this.setText(msg);
        this.setEditable(false);
        this.setFont(makeFont(18));
        this.setLineWrap(true);
        this.setWrapStyleWord(true);
        this.setBorder(BorderFactory.createEmptyBorder());
    }
}

package ui.modern;

import javax.swing.*;
import java.awt.*;

import static ui.pages.BankingApp.bluish;

// Represents a modernized JButton
public class JButtonModern extends JButton {
    public JButtonModern(String content) {
        this.setText(content);
        this.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        this.setForeground(Color.WHITE);
        this.setBackground(bluish);
        this.setSize(200, 40);
        this.setBorder(BorderFactory.createEmptyBorder());
    }
}

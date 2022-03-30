package ui.modern;

import javax.swing.*;
import java.awt.*;

public class JButtonModern extends JButton {
    public JButtonModern(String msg) {
        this.setText(msg);
        this.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        this.setForeground(Color.WHITE);
        this.setBackground(new Color(17, 70, 144));
        this.setSize(110, 40);
        this.setBorder(BorderFactory.createEmptyBorder());
    }
}

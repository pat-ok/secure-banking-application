package ui.modern;

import javax.swing.*;
import java.awt.*;

public class JLabelModern extends JLabel {
    public JLabelModern(String message) {
        this.setText(message);
        this.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        this.setSize(300, 40);
    }
}

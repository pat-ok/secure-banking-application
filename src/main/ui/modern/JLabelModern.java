package ui.modern;

import javax.swing.*;
import java.awt.*;

public class JLabelModern extends JLabel {
    public JLabelModern(String msg) {
        this.setText(msg);
        this.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        this.setSize(200, 40);
    }
}

package ui.modern;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class JTextFieldModern extends JTextField {
    private String content;

    public JTextFieldModern(String content) {
        this.content = content;
        this.setText(content);
        this.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        this.setForeground(Color.GRAY);
        this.setSize(200, 40);
        this.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
        setFocusAdapter();
    }

    private void setFocusAdapter() {
        this.addFocusListener(new FocusAdapter() {

            @Override
            public void focusGained(FocusEvent e) {
                if (getText().equals(content)) {
                    setText("");
                    setForeground(Color.BLACK);
                } else {
                    setText(getText());
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (getText().equals(content) || getText().length() == 0) {
                    setText(content);
                    setForeground(Color.GRAY);
                } else {
                    setText(getText());
                    setForeground(Color.BLACK);
                }
            }
        });
    }
}

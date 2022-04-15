package ui.modern;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

// Represents a modernized JTextField
public class JTextFieldModern extends JTextField {
    private final String prompt;

    public JTextFieldModern(String prompt) {
        this.prompt = prompt;
        this.setText(prompt);
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
                if (getText().equals(prompt)) {
                    setText("");
                    setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (getText().equals(prompt) || getText().length() == 0) {
                    setText(prompt);
                    setForeground(Color.GRAY);
                }
            }
        });
    }
}

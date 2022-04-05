package ui.modern;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

// Represents a modernized JPasswordField
public class JPasswordFieldModern extends JPasswordField {
    private final String prompt;

    public JPasswordFieldModern(String prompt) {
        this.prompt = prompt;
        this.setEchoChar((char)0);
        this.setText(prompt);
        this.setForeground(Color.GRAY);
        this.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        this.setSize(200, 40);
        this.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
        setFocusAdapter();
    }

    private void setFocusAdapter() {
        this.addFocusListener(new FocusAdapter() {

            @Override
            public void focusGained(FocusEvent e) {
                if (String.valueOf(getPassword()).equals(prompt)) {
                    setText("");
                    setForeground(Color.BLACK);
                    setEchoChar('•');
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (getPassword().length == 0) {
                    setEchoChar((char)0);
                    setText(prompt);
                    setForeground(Color.GRAY);
                }
            }
        });
    }
}

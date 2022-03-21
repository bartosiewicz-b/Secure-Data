package com.secureData.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;

public class SetPasswordDialog extends JDialog {

    private final JPasswordField passwordField;
    private final JPasswordField confirmPassword;

    public SetPasswordDialog() {
        setLocationRelativeTo(null);
        setTitle("Secure Data - Set Password");
        setModal(true);
        setSize(500, 250);
        setLayout(null);

        JLabel label = new JLabel("Password:");
        label.setBounds(100, 20, 300, 20);

        passwordField = new JPasswordField();
        passwordField.setBounds(100, 50, 300, 20);

        JLabel confirmLabel = new JLabel("Confirm Password:");
        confirmLabel.setBounds(100, 80, 300, 20);

        confirmPassword = new JPasswordField();
        confirmPassword.setBounds(100, 110, 300, 20);

        JLabel warningMatchingLabel = new JLabel("Passwords don't match!");
        warningMatchingLabel.setBounds(100, 130, 300, 20);
        warningMatchingLabel.setForeground(new Color(200, 0, 0));
        warningMatchingLabel.setVisible(false);

        JLabel warningEmptyLabel = new JLabel("You need to set a password!");
        warningEmptyLabel.setBounds(100, 130, 300, 20);
        warningEmptyLabel.setForeground(new Color(200, 0, 0));
        warningEmptyLabel.setVisible(false);

        JButton submit = new JButton("Submit");
        submit.setBounds(190, 160, 120, 30);
        submit.addActionListener(a -> {
            if(passwordField.getPassword().length > 0 && Arrays.equals(passwordField.getPassword(), confirmPassword.getPassword()))
                setVisible(false);
            else if(passwordField.getPassword().length == 0) {
                warningEmptyLabel.setVisible(true);
                warningMatchingLabel.setVisible(false);
            } else {
                warningEmptyLabel.setVisible(false);
                warningMatchingLabel.setVisible(true);
            }
        });

        add(label);
        add(passwordField);
        add(confirmLabel);
        add(confirmPassword);
        add(warningMatchingLabel);
        add(warningEmptyLabel);
        add(submit);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                passwordField.setText("");
            }
        });
    }

    public char[] getPassword() {
        setVisible(true);

        return passwordField.getPassword();
    }
}

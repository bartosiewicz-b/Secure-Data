package com.secureData.components;

import com.secureData.services.CryptoServiceImpl;
import com.secureData.services.IOServiceImpl;
import com.secureData.services.interf.CryptoService;
import com.secureData.services.interf.IOService;

import javax.swing.*;
import java.awt.*;

public class FileSaver {

    public JFileChooser fileChooser;

    public FileSaver(char[] text) {
        fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)
            new PasswordSetter(text);
    }

    class PasswordSetter {

        public PasswordSetter(char[] text) {
            JFrame frame = new JFrame("Secure Data - Password");
            frame.setSize(500, 230);
            frame.setLayout(null);

            JLabel passwordLabel = new JLabel("Password:");
            passwordLabel.setBounds(100, 20, 300, 20);
            frame.add(passwordLabel);

            JPasswordField passwordField = new JPasswordField();
            passwordField.setBounds(100, 50, 300, 20);
            frame.add(passwordField);

            JLabel confirmPasswordLabel = new JLabel("Confirm password:");
            confirmPasswordLabel.setBounds(100, 70, 300, 20);
            frame.add(confirmPasswordLabel);

            JPasswordField confirmPasswordField = new JPasswordField();
            confirmPasswordField.setBounds(100, 100, 300, 20);
            frame.add(confirmPasswordField);

            JLabel passwordNotMatching = new JLabel("The passwords aren't matching!");
            passwordNotMatching.setBounds(100, 120, 300, 20);
            passwordNotMatching.setForeground(Color.RED);
            passwordNotMatching.setVisible(false);
            frame.add(passwordNotMatching);

            JButton submit = new JButton("Submit");
            submit.setBounds(200, 160, 100, 20);
            submit.addActionListener(e -> {
                try {
                    CryptoService cryptoService = new CryptoServiceImpl();
                    IOService ioService = new IOServiceImpl();

                    if(cryptoService.comparePasswords(passwordField.getPassword(), confirmPasswordField.getPassword())) {
                        byte[] out = cryptoService.encrypt(text, passwordField.getPassword());
                        ioService.save(out, fileChooser.getSelectedFile().getAbsolutePath());
                        System.exit(0);
                    } else {
                        passwordNotMatching.setVisible(true);
                    }
                } catch (Exception ignored) {
                }
            });

            frame.add(submit);

            frame.setVisible(true);
        }
    }
}

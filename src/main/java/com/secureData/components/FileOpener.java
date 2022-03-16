package com.secureData.components;

import com.secureData.services.CryptoServiceImpl;
import com.secureData.services.IOServiceImpl;
import com.secureData.services.interf.CryptoService;
import com.secureData.services.interf.IOService;

import javax.swing.*;
import java.awt.*;

public class FileOpener {

    public JFileChooser fileChooser;

    public FileOpener() {
        fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
            new PasswordGetter();
    }

    class PasswordGetter {

        public PasswordGetter() {
            JFrame frame = new JFrame("Secure Data - Password");
            frame.setSize(500, 200);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setLayout(null);

            JLabel passwordLabel = new JLabel("Password:");
            passwordLabel.setBounds(100, 20, 300, 20);
            frame.add(passwordLabel);

            JPasswordField passwordField = new JPasswordField();
            passwordField.setBounds(100, 50, 300, 20);
            frame.add(passwordField);

            JLabel wrongPassword = new JLabel("Wrong Password!");
            wrongPassword.setBounds(100, 70, 300, 20);
            wrongPassword.setForeground(Color.RED);
            wrongPassword.setVisible(false);
            frame.add(wrongPassword);

            JButton submit = new JButton("Submit");
            submit.setBounds(200, 130, 100, 20);
            submit.addActionListener(e -> {

                CryptoService cryptoService = new CryptoServiceImpl();
                IOService ioService = new IOServiceImpl();

                try {
                    byte[] crypt = ioService.read(fileChooser.getSelectedFile().getAbsolutePath());
                    char[] text = cryptoService.decrypt(crypt, passwordField.getPassword());
                    frame.setVisible(false);
                    new NoteWindow(text);

                } catch (Exception ignored) {
                    wrongPassword.setVisible(true);
                }
            });

            frame.add(submit);

            frame.setVisible(true);
        }
    }
}

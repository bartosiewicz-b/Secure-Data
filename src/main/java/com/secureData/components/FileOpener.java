package com.secureData.components;

import com.secureData.services.CryptoServiceImpl;
import com.secureData.services.IOServiceImpl;
import com.secureData.services.interf.CryptoService;
import com.secureData.services.interf.IOService;

import javax.swing.*;

public class FileOpener {

    public JFileChooser fileChooser;

    public FileOpener() {
        fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(null) == JFileChooser.CANCEL_OPTION) System.exit(0);

        new PasswordGetter();
    }

    class PasswordGetter {

        public PasswordGetter() {
            JFrame frame = new JFrame("Secure Data - Password");
            frame.setSize(500, 200);
            frame.setLayout(null);

            JLabel passwordLabel = new JLabel("Password:");
            passwordLabel.setBounds(100, 20, 300, 20);
            frame.add(passwordLabel);

            JPasswordField passwordField = new JPasswordField();
            passwordField.setBounds(100, 50, 300, 20);
            frame.add(passwordField);

            JButton submit = new JButton("Submit");
            submit.setBounds(200, 130, 100, 20);
            submit.addActionListener(e -> {
                frame.setVisible(false);

                CryptoService cryptoService = new CryptoServiceImpl();
                IOService ioService = new IOServiceImpl();

                try {
                    new NoteWindow(cryptoService.decrypt(ioService.read(fileChooser.getSelectedFile().getAbsolutePath()),
                            passwordField.getPassword()));
                } catch (Exception ignored) {
                    System.exit(0);
                }
            });

            frame.add(submit);

            frame.setVisible(true);
        }
    }
}

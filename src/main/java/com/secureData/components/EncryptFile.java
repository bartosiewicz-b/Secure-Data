package com.secureData.components;

import com.secureData.services.CryptoServiceImpl;
import com.secureData.services.IOServiceImpl;
import com.secureData.services.interf.CryptoService;
import com.secureData.services.interf.IOService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class EncryptFile {

    public EncryptFile() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

            JDialog passwordDialog = new JDialog();
            passwordDialog.setLocationRelativeTo(null);
            passwordDialog.setTitle("Secure Data - Password");
            passwordDialog.setModal(true);
            passwordDialog.setSize(500, 200);
            passwordDialog.setLayout(null);

            JLabel label = new JLabel("Password:");
            label.setBounds(100, 20, 300, 20);

            JPasswordField passwordField = new JPasswordField();
            passwordField.setBounds(100, 50, 300, 20);

            JLabel wrongPassword = new JLabel("Wrong password!");
            wrongPassword.setBounds(100, 80, 300, 20);
            wrongPassword.setForeground(new Color(200, 0, 0));
            wrongPassword.setVisible(false);

            JButton submit = new JButton("Submit");
            submit.setBounds(190, 100, 120, 30);
            submit.addActionListener(a -> {
                CryptoService cryptoService = new CryptoServiceImpl();
                IOService ioService = new IOServiceImpl();

                try {
                    byte[] crypt = ioService.read(fileChooser.getSelectedFile().getAbsolutePath());
                    char[] text = cryptoService.decrypt(crypt, passwordField.getPassword());
                    passwordDialog.setVisible(false);
                    new MainWindow(text);

                } catch (Exception ignored) {
                    wrongPassword.setVisible(true);
                }
            });

            passwordDialog.add(label);
            passwordDialog.add(passwordField);
            passwordDialog.add(wrongPassword);
            passwordDialog.add(submit);

            passwordDialog.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
            });

            passwordDialog.setVisible(true);
        }
    }
}

package com.secureData;

import com.secureData.controllers.CryptEditor;
import com.secureData.services.CryptoServiceImpl;
import com.secureData.services.IOServiceImpl;
import com.secureData.services.interf.CryptoService;
import com.secureData.services.interf.IOService;

import javax.swing.*;

public class SecureData {

    public static void main(String... args) {

        int type = JOptionPane.showOptionDialog(null, "Choose operation:", "Secure Data",
                JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null,
                new String[] {"Encrypt", "Decrypt"},
                null);

        if(type == -1)
            return;

        if(type == 1) {
            try {
                JFileChooser fileChooser = new JFileChooser();
                int response = fileChooser.showOpenDialog(null);

                if(response == JFileChooser.CANCEL_OPTION) return;

                IOService ioService = new IOServiceImpl();
                CryptoService cryptoService = new CryptoServiceImpl();

                byte[] read = ioService.read(fileChooser.getSelectedFile().getAbsolutePath());

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
                submit.addActionListener(event -> {
                    frame.setVisible(false);
                    try {
                        new CryptEditor(cryptoService.decrypt(read, passwordField.getPassword()));
                    } catch (Exception ignored) {
                        JOptionPane.showMessageDialog(null, "Unexpected Error!", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        System.exit(0);
                    }
                });

                frame.add(submit);

                frame.setVisible(true);

            } catch (Exception ignored) {

                JOptionPane.showMessageDialog(null, "Unexpected Error!", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }else if (type == 0) {
            new CryptEditor(null);
        }
    }
}

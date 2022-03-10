package com.secureData.controllers;

import com.secureData.services.CryptoServiceImpl;
import com.secureData.services.IOServiceImpl;
import com.secureData.services.interf.CryptoService;
import com.secureData.services.interf.IOService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CryptEditor extends JFrame implements ActionListener {

    private final JTextArea textArea;

    public CryptEditor(char[] input) {

        setTitle("Secure Data");
        setSize(1000, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        textArea = new JTextArea();
        if(input != null) textArea.setText(new String(input));

        add(textArea);

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");

        JMenuItem saveItem = new JMenuItem("Save");

        saveItem.addActionListener(this);


        menu.add(saveItem);
        menuBar.add(menu);
        setJMenuBar(menuBar);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            CryptoService cryptoService = new CryptoServiceImpl();
            IOService ioService = new IOServiceImpl();

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
                    byte[] out = cryptoService.encrypt(textArea.getText().toCharArray(), passwordField.getPassword());
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.showSaveDialog(null);
                    ioService.save(out, fileChooser.getSelectedFile().getAbsolutePath());
                } catch (Exception ignored) {
                }finally {
                    System.exit(0);
                }
            });

            frame.add(submit);

            frame.setVisible(true);

        }catch (Exception ignored) {}
    }
}

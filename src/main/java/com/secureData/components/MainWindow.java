package com.secureData.components;

import com.secureData.services.CryptoServiceImpl;
import com.secureData.services.IOServiceImpl;
import com.secureData.services.interf.CryptoService;
import com.secureData.services.interf.IOService;

import javax.swing.*;
import java.util.Arrays;

public class MainWindow extends JFrame{

    public MainWindow() {this(null);}

    public MainWindow(char[] input) {

        CryptoService cryptoService = new CryptoServiceImpl();
        IOService ioService = new IOServiceImpl();

        setTitle("Secure Data");
        setSize(1000, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTextArea textArea = new JTextArea();
        textArea.setLineWrap(true);
        if(input != null) textArea.setText(new String(input));

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        add(scrollPane);

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");

        JMenuItem saveItem = new JMenuItem("Save");

        saveItem.addActionListener(a -> {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {

                SetPasswordDialog setPasswordDialog = new SetPasswordDialog();
                char[] password = setPasswordDialog.getPassword();


                try {
                    if(password.length > 0) {
                        ioService.save(
                                cryptoService.encrypt(textArea.getText().toCharArray(), password),
                                fileChooser.getSelectedFile().getAbsolutePath()
                        );

                        JOptionPane.showMessageDialog(this, "File saved :)",
                                "Secure Data", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (Exception ignored) {
                    JOptionPane.showMessageDialog(this, "Unexpected error occurred!",
                            "Secure Data - Error",JOptionPane.ERROR_MESSAGE);
                } finally {
                    Arrays.fill(password, '0');
                }
            }
        });

        menu.add(saveItem);
        menuBar.add(menu);
        setJMenuBar(menuBar);

        setVisible(true);
    }
}

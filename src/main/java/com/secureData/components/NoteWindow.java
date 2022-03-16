package com.secureData.components;

import javax.swing.*;

public class NoteWindow extends JFrame{

    public NoteWindow(char[] input) {

        setTitle("Secure Data");
        setSize(1000, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JTextArea textArea = new JTextArea();

        if(input != null) textArea.setText(new String(input));

        add(textArea);

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");

        JMenuItem saveItem = new JMenuItem("Save");

        saveItem.addActionListener(e -> new FileSaver(textArea.getText().toCharArray()));

        menu.add(saveItem);
        menuBar.add(menu);
        setJMenuBar(menuBar);

        setVisible(true);
    }
}

package com.secureData;

import com.secureData.components.FileOpener;
import com.secureData.components.NoteWindow;

import javax.swing.*;

public class SecureData {

    public static void main(String... args) {

        int type = JOptionPane.showOptionDialog(null, "Choose operation:", "Secure Data",
                JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null,
                new String[] {"Encrypt", "Decrypt"},
                null);

        switch (type) {
            case 0 -> new NoteWindow(null);
            case 1 -> new FileOpener();
        }
    }
}

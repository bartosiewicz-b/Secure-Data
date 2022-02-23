package com.secureData;

import com.secureData.controllers.FileCryptoControllerConsole;
import com.secureData.controllers.interf.FileCryptoController;

public class SecureData {

    public static void main(String... args) {

        if(args.length == 1 && args[0].equals("--help")) {
            help();
            return;
        }

        if(args.length != 2 || (!args[0].equals("e") && !args[0].equals("d"))) {
            System.out.println("Wrong number, or order of arguments. Run program again with '--help' to get more info.");
            return;
        }

        FileCryptoController fileCryptoController = new FileCryptoControllerConsole();

        switch (args[0]) {
            case "e" -> fileCryptoController.encrypt(args[1]);
            case "d" -> fileCryptoController.decrypt(args[1]);
            default -> System.out.println("First argument is unknown. Run program again with '--help' to get more info.");
        }
    }

    private static void help() {
        System.out.println("This program needs two arguments:");
        System.out.println("- encryption type:");
        System.out.println("    - 'e' - for encryption");
        System.out.println("    - 'd' - for decryption");
        System.out.println("- filename");
    }
}

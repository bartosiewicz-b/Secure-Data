package com.secureData.controllers;

import com.secureData.controllers.interf.FileCryptoController;
import com.secureData.services.CryptoServiceImpl;
import com.secureData.services.IOServiceImpl;
import com.secureData.services.interf.CryptoService;
import com.secureData.services.interf.IOService;

import java.io.Console;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class FileCryptoControllerConsole implements FileCryptoController {

    CryptoService cryptoService = new CryptoServiceImpl();
    IOService ioService = new IOServiceImpl();

    @Override
    public void encrypt(String filename) {
        Console console = System.console();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the data to be encrypted:");
        char[] data = scanner.nextLine().toCharArray();

        char[] password;
        char[] confirmPassword;
        do {
            password = console.readPassword("Enter password: ");
            confirmPassword = console.readPassword("Confirm password: ");
        } while(!Arrays.equals(password, confirmPassword));
        Arrays.fill(confirmPassword, '0');

        try {
            ioService.save(cryptoService.encrypt(data, password), filename);

        } catch (Exception ignored) {
            System.out.println("Error has occurred!");

        } finally {
            Arrays.fill(data, '0');
            Arrays.fill(password, '0');
        }
    }

    @Override
    public void decrypt(String filename) {
        Console console = System.console();

        char[] password = console.readPassword("Enter password: ");
        char[] data = new char[0];

        try {
            data = cryptoService.decrypt(ioService.read(filename), password);

            for(char i: data)
                System.out.print(i);
            System.out.println();

        } catch (IOException ignored) {
            System.out.println("Unable to read the file!");

        }
        catch (Exception ignored) {
            System.out.println("Incorrect password or salt!");

        } finally {
            Arrays.fill(password, '0');
            Arrays.fill(data, '0');
        }
    }
}

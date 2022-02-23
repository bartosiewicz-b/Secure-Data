package com.secureData.services;

import com.secureData.services.interf.IOService;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


public class IOServiceImpl implements IOService {

    @Override
    public void save(byte[] data, String filename) throws IOException {

        try(FileOutputStream fileOutputStream = new FileOutputStream(filename)) {
            fileOutputStream.write(data);
        }
    }

    @Override
    public byte[] read(String filename) throws IOException {

        try(FileInputStream fileInputStream = new FileInputStream(filename)) {
            return fileInputStream.readAllBytes();
        }
    }
}

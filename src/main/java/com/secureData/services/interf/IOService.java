package com.secureData.services.interf;

import java.io.IOException;

public interface IOService {

    void save(byte[] data, String filename) throws IOException;
    byte[] read(String filename) throws IOException;
}

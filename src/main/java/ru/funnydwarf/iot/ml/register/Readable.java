package ru.funnydwarf.iot.ml.register;

import java.io.IOException;

public interface Readable {
    void readRegisterValueFromDevice(Object address) throws IOException, InterruptedException;
}

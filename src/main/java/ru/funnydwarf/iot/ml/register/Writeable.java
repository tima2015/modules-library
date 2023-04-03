package ru.funnydwarf.iot.ml.register;

import java.io.IOException;

public interface Writeable {
    void writeCurrentRegisterValueToDevice(Object address) throws IOException, InterruptedException;
}

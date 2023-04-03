package ru.funnydwarf.iot.ml.register;

import java.io.IOException;

public interface Writeable<AddressT> {
    void writeCurrentRegisterValueToDevice(AddressT address) throws IOException, InterruptedException;
}

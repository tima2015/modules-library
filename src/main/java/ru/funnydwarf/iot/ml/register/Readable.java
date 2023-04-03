package ru.funnydwarf.iot.ml.register;

import java.io.IOException;

public interface Readable <AddressT>{
    void readRegisterValueFromDevice(AddressT address) throws IOException, InterruptedException;
}

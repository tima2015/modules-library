package ru.funnydwarf.iot.ml.receiver.writer;

import ru.funnydwarf.iot.nml.DigitalValue;

public interface Writer {
    void write(Object address, DigitalValue value);
}

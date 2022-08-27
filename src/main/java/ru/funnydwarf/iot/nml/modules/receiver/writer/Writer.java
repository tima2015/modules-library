package ru.funnydwarf.iot.nml.modules.receiver.writer;

import ru.funnydwarf.iot.nml.DigitalValue;

public interface Writer {
    void write(Object address, DigitalValue value);
}

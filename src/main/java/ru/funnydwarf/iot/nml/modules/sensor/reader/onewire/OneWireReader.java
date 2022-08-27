package ru.funnydwarf.iot.nml.modules.sensor.reader.onewire;

import ru.funnydwarf.iot.nml.modules.sensor.reader.Reader;

public abstract class OneWireReader implements Reader {
    protected static final String ONE_WIRE_SLAVES_PATH = "/sys/bus/w1/devices/";
}

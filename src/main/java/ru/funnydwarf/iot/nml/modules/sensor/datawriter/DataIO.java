package ru.funnydwarf.iot.nml.modules.sensor.datawriter;

import ru.funnydwarf.iot.nml.modules.sensor.MeasurementData;

import java.io.IOException;

public interface DataIO {
    void write(MeasurementData[] data, String name) throws IOException;

    MeasurementData[][] read(String name, int offset, int length) throws IOException;
}

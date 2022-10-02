package ru.funnydwarf.iot.ml.sensor.datawriter;

import ru.funnydwarf.iot.ml.sensor.MeasurementData;

import java.io.IOException;

public interface DataIO {
    void write(MeasurementData[] data, String name) throws IOException;

    MeasurementData[][] read(String name,String[] units, int offset, int length) throws IOException;
}

package ru.funnydwarf.iot.ml.sensor.dataio;

import ru.funnydwarf.iot.ml.sensor.MeasurementData;

import java.io.IOException;

public interface DataOutput {
    
    void write(MeasurementData data, String measurementId) throws IOException;
    
}

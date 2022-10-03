package ru.funnydwarf.iot.ml.sensor.dataio;

import ru.funnydwarf.iot.ml.sensor.MeasurementData;

import java.io.IOException;

public interface DataInput {

    MeasurementData[] read(String measurementId, String unit, int offset, int lenght) throw IOException;

}

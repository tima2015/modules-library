package ru.funnydwarf.iot.ml.sensor.reader;

import ru.funnydwarf.iot.ml.sensor.MeasurementData;

public interface Reader {
    MeasurementData[] read(Object address);

}

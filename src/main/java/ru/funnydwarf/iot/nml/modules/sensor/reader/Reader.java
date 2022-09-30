package ru.funnydwarf.iot.nml.modules.sensor.reader;

import ru.funnydwarf.iot.nml.modules.sensor.MeasurementData;

public interface Reader {
    MeasurementData[] read(Object address);

}

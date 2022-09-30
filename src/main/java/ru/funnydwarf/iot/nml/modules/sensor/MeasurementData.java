package ru.funnydwarf.iot.nml.modules.sensor;

import java.util.Date;

public record MeasurementData(double value, String unitName, String measurementName, Date measurementDate) {

    public static MeasurementData createToCurrentDate(double value, String unitName, String measurementName) {
        return new MeasurementData(value, unitName, measurementName, new Date());
    }
}

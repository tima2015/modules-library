package ru.funnydwarf.iot.nml.modules.sensor;

import ru.funnydwarf.iot.nml.modules.Module;
import ru.funnydwarf.iot.nml.modules.sensor.reader.Reader;

public class Sensor extends Module {

    private double measurementValue = 0;

    private final Reader reader;

    public Sensor(Reader reader, Object address, String name, String description) {
        super(address, name, description);
        this.reader = reader;
    }

    public Sensor(Reader reader, Object address, String name, String description, String userCustomName, String userCustomDescription) {
        super(address, name, description, userCustomName, userCustomDescription);
        this.reader = reader;
    }

    public double getMeasurementValue() {
        return measurementValue;
    }

    public void updateMeasurement() {
        try {
            measurementValue = reader.read(getAddress());
        } catch (Exception e) {
            e.printStackTrace();
            measurementValue = Integer.MIN_VALUE;
        }
    }
}

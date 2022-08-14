package ru.funnydwarf.iot.nml.modules.onewire;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.funnydwarf.iot.nml.Pin;

public class DS18B20OneWireUnit extends OneWireUnit {

    @JsonIgnore
    private final double temperatureFromLastMeasurements = Double.NaN;

    public DS18B20OneWireUnit(Pin pin, String name, String description, long rom) {
        super(pin, name, description, rom);
    }

    public DS18B20OneWireUnit(@JsonProperty("pin") Pin pin,
                              @JsonProperty("name") String name,
                              @JsonProperty("description") String description,
                              @JsonProperty("userCustomName") String userCustomName,
                              @JsonProperty("userCustomDescription") String userCustomDescription,
                              @JsonProperty("rom") long rom) {
        super(pin, name, description, userCustomName, userCustomDescription, rom);
    }

    private static native void takeMeasurements(int pin);

    public double takeMeasurementsAndGetResult() {
        return 0;
    }

    private static native short getTemperatureFromUnit(int pin);

    public double getTemperatureFromLastMeasurements() {
        return 0;
    }
}

package ru.funnydwarf.iot.nml.modules.onewire;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.funnydwarf.iot.nml.Pin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class DS18B20OneWireUnit extends OneWireUnit {

    @JsonIgnore
    private final String modulePath;

    public DS18B20OneWireUnit(Pin pin, String name, String description, String id) {
        super(pin, name, description, id);
        modulePath = OneWire.ONE_WIRE_ROOT_PATH + id + '/';
    }

    public DS18B20OneWireUnit(@JsonProperty("pin") Pin pin,
                              @JsonProperty("name") String name,
                              @JsonProperty("description") String description,
                              @JsonProperty("userCustomName") String userCustomName,
                              @JsonProperty("userCustomDescription") String userCustomDescription,
                              @JsonProperty("id") String id) {
        super(pin, name, description, userCustomName, userCustomDescription, id);
        modulePath = OneWire.ONE_WIRE_ROOT_PATH + id + '/';
    }

    public double takeMeasurements() {
        try {
            String result = Files.readString(new File(modulePath + "/w1_slave").toPath());
            String temperature = result.substring(result.lastIndexOf("t=") + 2).trim();
            return Integer.parseInt(temperature)/1000d;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public String toString() {
        return "DS18B20OneWireUnit{" +
                "modulePath='" + modulePath + '\'' +
                "} " + super.toString();
    }
}

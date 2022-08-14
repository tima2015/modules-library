package ru.funnydwarf.iot.nml.modules.onewire;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.funnydwarf.iot.nml.Pin;

public class UnknownOneWireUnit extends OneWireUnit{

    public UnknownOneWireUnit(Pin pin, String name, String description, long rom) {
        super(pin, name, description, rom);
    }

    public UnknownOneWireUnit(@JsonProperty("pin") Pin pin,
                              @JsonProperty("name") String name,
                              @JsonProperty("description") String description,
                              @JsonProperty("userCustomName") String userCustomName,
                              @JsonProperty("userCustomDescription") String userCustomDescription,
                              @JsonProperty("rom") long rom) {
        super(pin, name, description, userCustomName, userCustomDescription, rom);
    }

    @Override
    public String toString() {
        return "UnknownOneWireUnit{} " + super.toString();
    }
}

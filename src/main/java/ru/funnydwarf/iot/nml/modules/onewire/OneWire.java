package ru.funnydwarf.iot.nml.modules.onewire;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.funnydwarf.iot.nml.Initializable;
import ru.funnydwarf.iot.nml.Pin;
import ru.funnydwarf.iot.nml.modules.Module;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class OneWire extends Module implements Initializable {

    private final OneWireUnit[] oneWireUnits;

    public OneWire(Pin pin, String name, String description, OneWireUnit[] oneWireUnits) {
        super(pin, name, description);
        this.oneWireUnits = oneWireUnits;
    }

    @JsonCreator
    public OneWire(@JsonProperty("pin") Pin pin,
                   @JsonProperty("name") String name,
                   @JsonProperty("description") String description,
                   @JsonProperty("userCustomName") String userCustomName,
                   @JsonProperty("userCustomDescription") String userCustomDescription,
                   @JsonProperty("oneWireUnits") OneWireUnit[] oneWireUnits) {
        super(pin, name, description, userCustomName, userCustomDescription);
        this.oneWireUnits = oneWireUnits;
    }

    public OneWireUnit[] getOneWireUnits() {
        return oneWireUnits;
    }

    @Override
    public void initialize(){
        String[] connectedUnitsId = getConnectedUnitsId();
        System.out.println("connectedUnitsId = [" + Arrays.toString(connectedUnitsId) + "]");
        OneWireUnit.State state;
        for (OneWireUnit unit : oneWireUnits) {
            state = OneWireUnit.State.NOT_FOUND;
            for (String id : connectedUnitsId) {
                if (unit.getId().equals(id)){
                    state = OneWireUnit.State.OK;
                    break;
                }
            }
            unit.setState(state);
        }
    }

    static final String ONE_WIRE_ROOT_PATH = "/sys/bus/w1/devices/";

    @JsonIgnore
    public static String[] getConnectedUnitsId() {
        return new File(ONE_WIRE_ROOT_PATH).list();
    }

    @Override
    public String toString() {
        return "OneWire{" +
                "oneWireUnits=" + Arrays.toString(oneWireUnits) +
                "} " + super.toString();
    }
}

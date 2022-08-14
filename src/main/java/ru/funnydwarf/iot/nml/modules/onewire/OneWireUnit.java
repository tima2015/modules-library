package ru.funnydwarf.iot.nml.modules.onewire;

import com.fasterxml.jackson.annotation.*;
import ru.funnydwarf.iot.nml.Pin;
import ru.funnydwarf.iot.nml.modules.Module;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property="type")
@JsonSubTypes({
        @JsonSubTypes.Type(value= DS18B20OneWireUnit.class, name="DS18B20"),
        @JsonSubTypes.Type(value= UnknownOneWireUnit.class, name="Unknown")
})
public abstract class OneWireUnit extends Module{

    public enum State {
        WAIT_INITIALIZE,
        NOT_FOUND,
        OK
    }

    @JsonIgnore
    private State state = State.WAIT_INITIALIZE;

    /**
     * Уникальный индетификатор устройства OneWire
     */
    private final String id;

    public OneWireUnit(Pin pin, String name, String description, String id) {
        super(pin, name, description);
        this.id = id;
    }

    @JsonCreator
    public OneWireUnit(@JsonProperty("pin") Pin pin,
                       @JsonProperty("name") String name,
                       @JsonProperty("description") String description,
                       @JsonProperty("userCustomName") String userCustomName,
                       @JsonProperty("userCustomDescription") String userCustomDescription,
                       @JsonProperty("id") String id) {
        super(pin, name, description, userCustomName, userCustomDescription);
        this.id = id;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "OneWireUnit{" +
                "state=" + state +
                ", rom=" + id +
                "} " + super.toString();
    }
}

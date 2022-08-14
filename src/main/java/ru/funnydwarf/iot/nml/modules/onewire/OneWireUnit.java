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
    private final long rom;

    public OneWireUnit(Pin pin, String name, String description, long rom) {
        super(pin, name, description);
        this.rom = rom;
    }

    @JsonCreator
    public OneWireUnit(@JsonProperty("pin") Pin pin,
                       @JsonProperty("name") String name,
                       @JsonProperty("description") String description,
                       @JsonProperty("userCustomName") String userCustomName,
                       @JsonProperty("userCustomDescription") String userCustomDescription,
                       @JsonProperty("rom") long rom) {
        super(pin, name, description, userCustomName, userCustomDescription);
        this.rom = rom;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public long getRom() {
        return rom;
    }
}

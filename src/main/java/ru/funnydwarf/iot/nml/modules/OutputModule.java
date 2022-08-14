package ru.funnydwarf.iot.nml.modules;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.funnydwarf.iot.nml.DigitalValue;
import ru.funnydwarf.iot.nml.Pin;
import ru.funnydwarf.iot.nml.PinMode;
import ru.funnydwarf.iot.nml.WrongPinModeException;

public class OutputModule extends Module{

    @JsonIgnore
    private DigitalValue currentState = DigitalValue.LOW;
    public OutputModule(Pin pin, String name, String description) {
        super(pin, name, description);
    }

    @JsonCreator
    public OutputModule(@JsonProperty("pin") Pin pin,
                        @JsonProperty("name") String name,
                        @JsonProperty("description") String description,
                        @JsonProperty("userCustomName") String userCustomName,
                        @JsonProperty("userCustomDescription") String userCustomDescription) {
        super(pin, name, description, userCustomName, userCustomDescription);
    }

    public DigitalValue getCurrentState() {
        return currentState;
    }

    public void setCurrentState(DigitalValue currentState) {
        this.currentState = currentState;
        if (getPin().getMode() != PinMode.OUTPUT) {
            try {
                getPin().setMode(PinMode.OUTPUT);
            } catch (WrongPinModeException e) {
                throw new RuntimeException(e);
            }
        }
        digitalWrite(getPin().getNumber(), currentState.value);
    }

    private static native void digitalWrite(int pin, int value);

    @Override
    public String toString() {
        return "OutputModule{" +
                "currentState=" + currentState +
                "} " + super.toString();
    }
}

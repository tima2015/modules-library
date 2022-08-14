package ru.funnydwarf.iot.nml;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Класс содержащий информацию о пине и его текущем состоянии
 */
@JsonAutoDetect
public class Pin {

    private final PinType type;

    private final int number;
    @JsonIgnore
    private PinMode mode;

    @JsonCreator
    public Pin(@JsonProperty("type") PinType type, @JsonProperty("number") int number) {
        this.type = type;
        this.number = number;
        mode = type == PinType.GPIO ? PinMode.UNKNOWN : PinMode.NONE;
    }

    public void setMode(PinMode mode) throws WrongPinModeException {
        if (type != PinType.GPIO) {
            if (mode == PinMode.NONE) {
                return;
            } else {
                throw new WrongPinModeException(this, mode);
            }
        }

        this.mode = mode;

        if (mode == PinMode.UNKNOWN) {
            return;
        }
        setMode(number, mode.code);
    }

    private static native void setMode(int pin, int modeCode);

    public PinType getType() {
        return type;
    }

    public int getNumber() {
        return number;
    }

    public PinMode getMode() {
        return mode;
    }

    @Override
    public String toString() {
        return "Pin{" +
                "type=" + type +
                ", pin=" + number +
                ", mode=" + mode +
                '}';
    }
}

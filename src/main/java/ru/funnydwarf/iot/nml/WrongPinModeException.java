package ru.funnydwarf.iot.nml;

public class WrongPinModeException extends Exception{

    private static final String message = "Can't change mode for %s to %s";

    public WrongPinModeException(Pin pin, Pin.PinMode mode) {
        super(String.format(message, pin.toString(), mode.toString()));
    }
}

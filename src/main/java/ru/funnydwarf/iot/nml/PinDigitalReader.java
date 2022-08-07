package ru.funnydwarf.iot.nml;

import java.util.Hashtable;

public class PinDigitalReader {

    private static final Hashtable<Pin, PinDigitalReader> WRITER_HASHTABLE = new Hashtable<>();
    private final Pin pin;

    private PinDigitalReader(Pin pin){
        this.pin = pin;
    }

    public static PinDigitalReader getInstance(Pin pin) {
        return WRITER_HASHTABLE.containsKey(pin) ?
                WRITER_HASHTABLE.get(pin) : WRITER_HASHTABLE.put(pin, new PinDigitalReader(pin));
    }

    public DigitalValue digitalRead(Pin pin) {
        if (pin.getMode() != Pin.PinMode.INPUT) {
            try {
                pin.setMode(Pin.PinMode.INPUT);
            } catch (WrongPinModeException e) {
                throw new RuntimeException(e);
            }
       }
        return digitalRead(pin.getWiringPiNumber()) == DigitalValue.LOW.value ? DigitalValue.LOW : DigitalValue.HIGH;
    }

    private static native int digitalRead(int pinNumber);

}

package ru.funnydwarf.iot.nml;

import java.util.Hashtable;

public class PinDigitalWriter {

    private static final Hashtable<Pin, PinDigitalWriter> WRITER_HASHTABLE = new Hashtable<>();
    private final Pin pin;

    private PinDigitalWriter(Pin pin){
        this.pin = pin;
    }

    public static PinDigitalWriter getInstance(Pin pin) {
        return WRITER_HASHTABLE.containsKey(pin) ?
                WRITER_HASHTABLE.get(pin) : WRITER_HASHTABLE.put(pin, new PinDigitalWriter(pin));
    }

    public void digitalWrite(Pin pin, DigitalValue value) {
        if (pin.getMode() != Pin.PinMode.OUTPUT) {
            try {
                pin.setMode(Pin.PinMode.OUTPUT);
            } catch (WrongPinModeException e) {
                throw new RuntimeException(e);
            }
        }
        digitalWrite(pin.getWiringPiNumber(), value.value);
    }

    private static native void digitalWrite(int pinNumber, int value);

}

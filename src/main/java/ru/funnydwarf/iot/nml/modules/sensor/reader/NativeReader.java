package ru.funnydwarf.iot.nml.modules.sensor.reader;

import org.springframework.stereotype.Component;

@Component
public class NativeReader implements Reader{

    private static native double digitalRead(int pin);
    @Override
    public double read(Object address) {
        return digitalRead((Integer) address);
    }
}

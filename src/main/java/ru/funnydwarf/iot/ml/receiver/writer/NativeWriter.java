package ru.funnydwarf.iot.ml.receiver.writer;

import org.springframework.stereotype.Component;
import ru.funnydwarf.iot.nml.DigitalValue;

@Component
public class NativeWriter implements Writer{
    private static native void digitalWrite(int pin, int value);

    @Override
    public void write(Object address, DigitalValue digitalValue) {
        digitalWrite((Integer) address, digitalValue.value);
    }
}

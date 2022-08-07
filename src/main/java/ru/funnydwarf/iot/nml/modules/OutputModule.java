package ru.funnydwarf.iot.nml.modules;

import ru.funnydwarf.iot.nml.Pin;
import ru.funnydwarf.iot.nml.PinDigitalWriter;

public class OutputModule implements Module{

    private final PinDigitalWriter writer;
    private final Pin pin;
    private final String name;

    OutputModule(Pin pin, String name){
        this.name = name;
        this.pin = pin;
        writer = PinDigitalWriter.getInstance(pin);
    }

    OutputModule(Pin pin){
        this(pin, Module.NO_NAME);
    }

    @Override
    public String getName() {
        return name;
    }

    public PinDigitalWriter getWriter() {
        return writer;
    }

    public Pin getPin() {
        return pin;
    }
}

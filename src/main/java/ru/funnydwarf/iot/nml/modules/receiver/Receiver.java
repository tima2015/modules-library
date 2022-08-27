package ru.funnydwarf.iot.nml.modules.receiver;

import ru.funnydwarf.iot.nml.DigitalValue;
import ru.funnydwarf.iot.nml.modules.Module;
import ru.funnydwarf.iot.nml.modules.receiver.writer.Writer;

public class Receiver extends Module {

    private DigitalValue current = null;
    private Writer writer;

    public Receiver(Writer writer, Object address, String name, String description) {
        super(address, name, description);
        this.writer = writer;
    }

    public Receiver(Writer writer, Object address, String name, String description, String userCustomName, String userCustomDescription) {
        super(address, name, description, userCustomName, userCustomDescription);
        this.writer = writer;
    }

    public DigitalValue getCurrent() {
        return current;
    }

    public void write(DigitalValue value) {
        current = value;
        try {
            writer.write(getAddress(), value);
        } catch (Exception e) {
            e.printStackTrace();
            current = null;
        }
    }
}

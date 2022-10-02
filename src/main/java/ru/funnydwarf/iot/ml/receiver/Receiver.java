package ru.funnydwarf.iot.ml.receiver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.funnydwarf.iot.nml.DigitalValue;
import ru.funnydwarf.iot.ml.Module;
import ru.funnydwarf.iot.ml.receiver.writer.Writer;

public class Receiver extends Module {

    public static final Logger log = LoggerFactory.getLogger(Receiver.class);

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
        log.debug("write() called with: value = [{}]", value);
        current = value;
        try {
            writer.write(getAddress(), value);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            current = null;
        }
    }
}

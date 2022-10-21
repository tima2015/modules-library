package ru.funnydwarf.iot.ml.receiver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.funnydwarf.iot.ml.Module;
import ru.funnydwarf.iot.ml.ModuleGroup;
import ru.funnydwarf.iot.ml.receiver.writer.Writer;

public class Receiver extends Module {

    public static final Logger log = LoggerFactory.getLogger(Receiver.class);

    private Object lastValue = null;
    private final Writer writer;

    public Receiver(Writer writer,ModuleGroup group, Object address, String name, String description) {
        this(writer, group, address, name,description, "", "");
    }

    public Receiver(Writer writer, ModuleGroup group, Object address, String name, String description, String userCustomName, String userCustomDescription) {
        super(group, address, name, description, userCustomName, userCustomDescription);
        this.writer = writer;
    }

    public Object getLastValue() {
        return lastValue;
    }

    public void write(Object value) {
        log.debug("write() called with: value = [{}]", value);
        lastValue = value;
        try {
            writer.write(getAddress(), value);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            lastValue = null;
        }
    }
}

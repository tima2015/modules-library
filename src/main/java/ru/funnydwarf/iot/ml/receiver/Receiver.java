package ru.funnydwarf.iot.ml.receiver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.funnydwarf.iot.ml.Module;
import ru.funnydwarf.iot.ml.ModuleGroup;
import ru.funnydwarf.iot.ml.receiver.writer.Writer;

/**
 * Приёмщик
 */
public class Receiver extends Module {

    public final Logger log;
    /**
     * Последнее значение переданное приёмщику
     */
    private Object lastValue = null;
    /**
     * Объект писателя передающего значение модулю
     */
    private final Writer writer;

    public Receiver(Writer writer, ModuleGroup group, Object address, String name, String description) {
        super(group, address, name, description);
        log = LoggerFactory.getLogger(name);
        this.writer = writer;
    }

    public Object getLastValue() {
        return lastValue;
    }

    /**
     * Передать значение модулю
     * @param value передаваемое модулю значение
     */
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

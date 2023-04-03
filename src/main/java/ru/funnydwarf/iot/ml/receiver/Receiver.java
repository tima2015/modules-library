package ru.funnydwarf.iot.ml.receiver;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ru.funnydwarf.iot.ml.InitializationState;
import ru.funnydwarf.iot.ml.Module;
import ru.funnydwarf.iot.ml.ModuleGroup;
import ru.funnydwarf.iot.ml.receiver.writer.Writer;

/**
 * Приёмщик
 */
@Getter
@Slf4j
public class Receiver <WriterT extends Writer, ModuleGroupT extends ModuleGroup, AddressT> extends Module<ModuleGroupT, AddressT> {

    /**
     * Последнее значение переданное приёмщику
     */
    private Object lastValue = null;
    /**
     * Объект писателя передающего значение модулю
     */
    @Getter(AccessLevel.NONE)
    private final WriterT writer;

    public Receiver(WriterT writer, ModuleGroupT group, AddressT address, String name, String description, Initializer initializer) {
        super(group, address, name, description, initializer);
        this.writer = writer;
    }

    /**
     * Передать значение модулю
     * @param value передаваемое модулю значение
     */
    public void write(Object value) {
        log.debug("[{}] write() called with: value = [{}]", getName(), value);
        if (getInitializationState() == InitializationState.NOT_INITIALIZED){
            log.warn("[{}] write: module have initialization error! Pass...", getName());
            return;
        }
        lastValue = value;
        try {
            writer.write(getAddress(), value);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            lastValue = null;
        }
    }
}

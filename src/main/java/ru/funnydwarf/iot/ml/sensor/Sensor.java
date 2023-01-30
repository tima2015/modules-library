package ru.funnydwarf.iot.ml.sensor;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ru.funnydwarf.iot.ml.InitializationState;
import ru.funnydwarf.iot.ml.Module;
import ru.funnydwarf.iot.ml.ModuleGroup;
import ru.funnydwarf.iot.ml.sensor.reader.Reader;

/**
 * Сенсор/Датчик
 */
@Getter
@Slf4j
public class Sensor extends Module {

    /**
     * Данные последних замеров
     */
    private Measurement[] measurementData;

    /**
     * Читающий показания датчика
     */
    @Getter(AccessLevel.NONE)
    private final Reader reader;

    /**
     * Аргументы для читателя
     */
    private final Object[] readerArgs;

    public Sensor(Reader reader, ModuleGroup group, Object address, String name, String description, Object ... readerArgs){
        super(group, address, name, description);
        this.reader = reader;
        measurementData = reader.getTemplateRead();
        this.readerArgs = readerArgs;
    }

    /**
     * Выполнить получение новых замеров и записать полученные данные в хранилище
     */
    public Measurement[] takeMeasurement() {
        log.debug("[{}] takeMeasurement() called", getName());
        measurementData = new Measurement[0];
        if (getInitializationState() == InitializationState.NOT_INITIALIZED){
            log.warn("[{}] takeMeasurement: module have initialization error! Pass...", getName());
            return measurementData;
        }
        try {
            measurementData = reader.read(getAddress(), readerArgs);
        } catch (Exception e) {
            log.error("[{}] {}", getName(), e.getMessage(), e);
        }
        return measurementData;
    }
}

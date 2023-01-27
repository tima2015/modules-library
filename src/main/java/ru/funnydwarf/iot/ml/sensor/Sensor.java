package ru.funnydwarf.iot.ml.sensor;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ru.funnydwarf.iot.ml.InitializationState;
import ru.funnydwarf.iot.ml.Module;
import ru.funnydwarf.iot.ml.ModuleGroup;
import ru.funnydwarf.iot.ml.sensor.reader.Reader;

import java.util.List;

/**
 * Сенсор/Датчик
 */
@Getter
@Slf4j
public class Sensor extends Module {

    /**
     * Данные последних замеров
     */
    private MeasurementData[] measurementData;
    /**
     * Идентификаторы замеров
     */
    @Getter(AccessLevel.NONE)
    private final String[] measurementIDs;

    /**
     * Читающий показания датчика
     */
    @Getter(AccessLevel.NONE)
    private final Reader reader;
    /**
     * Список с историей замеров
     */
    private final List<MeasurementData>[] measurementDataList;

    /**
     * Аргументы для читателя
     */
    private Object[] readerArgs = new Object[0];

    public Sensor(Reader reader, List<MeasurementData>[] measurementDataList, ModuleGroup group, Object address, String name, String description) {
        super(group, address, name, description);
        this.reader = reader;
        this.measurementDataList = measurementDataList;
        measurementData = reader.getTemplateRead();
        measurementIDs = new String[measurementData.length];
        for (int i = 0; i < measurementData.length; i++) {
            measurementIDs[i] = name + '_' + measurementData[i].measurementName();
        }
    }

    public Sensor(Reader reader, List<MeasurementData>[] measurementDataList, ModuleGroup group, Object address, String name, String description, Object ... readerArgs){
        this(reader, measurementDataList, group, address, name, description);
        this.readerArgs = readerArgs;
    }

    public Sensor(Reader reader, ModuleGroup group, Object address, String name, String description, Object... readerArgs){
        this(reader, null, group, address, name, description, readerArgs);
    }

    /**
     * Выполнить получение новых замеров и записать полученные данные в хранилище
     */
    public void updateMeasurement() {
        log.debug("[{}] updateMeasurement() called", getName());
        if (getInitializationState() == InitializationState.NOT_INITIALIZED){
            log.warn("[{}] updateMeasurement: module have initialization error! Pass...", getName());
            return;
        }
        try {
            measurementData = reader.read(getAddress(), readerArgs);
            if (measurementDataList == null){
                return;
            }
            for (int i = 0; i < measurementDataList.length; i++) {
                measurementDataList[i].add(measurementData[i]);
            }
        } catch (Exception e) {
            log.error("[{}] {}", getName(), e.getMessage(), e);
            measurementData = new MeasurementData[0];
        }
    }
}

package ru.funnydwarf.iot.ml.sensor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.funnydwarf.iot.ml.InitializationState;
import ru.funnydwarf.iot.ml.Module;
import ru.funnydwarf.iot.ml.ModuleGroup;
import ru.funnydwarf.iot.ml.sensor.dataio.DataInput;
import ru.funnydwarf.iot.ml.sensor.dataio.DataOutput;
import ru.funnydwarf.iot.ml.sensor.reader.Reader;

import java.io.IOException;

/**
 * Сенсор/Датчик
 */
public class Sensor extends Module {
    private final Logger log;
    /**
     * Данные последних замеров
     */
    private MeasurementData[] measurementData;
    /**
     * Идентификаторы замеров
     */
    private final String[] measurementIDs;

    /**
     * Читающий показания датчика
     */
    private final Reader reader;
    /**
     * Ввод данных для просмотра информации о замерах
     */
    private final DataInput dataInput;
    /**
     * Вывод данных для сохранения замеров
     */
    private final DataOutput dataOutput;

    /**
     * Аргументы для читателя
     */
    private Object[] readerArgs = new Object[0];

    public Sensor(Reader reader, DataInput dataInput, DataOutput dataOutput, ModuleGroup group, Object address, String name, String description) {
        super(group, address, name, description);
        log = LoggerFactory.getLogger(name);
        this.reader = reader;
        this.dataInput = dataInput;
        this.dataOutput = dataOutput;
        measurementData = reader.getTemplateRead();
        measurementIDs = new String[measurementData.length];
        for (int i = 0; i < measurementData.length; i++) {
            measurementIDs[i] = name + '_' + measurementData[i].measurementName();
        }
    }

    public Sensor(Reader reader, DataInput dataInput, DataOutput dataOutput, ModuleGroup group, Object address, String name, String description, Object ... readerArgs){
        this(reader, dataInput, dataOutput, group, address, name, description);
        this.readerArgs = readerArgs;
    }

    public MeasurementData[] getMeasurementData() {
        return measurementData;
    }

    public Object[] getReaderArgs() {
        return readerArgs;
    }

    /**
     * Просмотреть историю замеров
     * @param offset отступ в количестве замеров от последнего замера
     * @param length количество возвращаемых замеров
     * @return массив замеров начинающихся с offset-того замера (от последнего замера) и размера length
     */
    public MeasurementData[][] getHistoryMeasurementValue(int offset, int length){
        log.debug("getHistoryMeasurementValue() called with: offset = [{}], length = [{}]", offset, length);
        MeasurementData[][] history = new MeasurementData[measurementData.length][];
        try {
            for (int i = 0; i < history.length; i++) {
                MeasurementData data = measurementData[i];
                history[i] = dataInput.read(measurementIDs[i], data.measurementName(), data.unitName(), offset, length);
            }
            return history;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return new MeasurementData[0][0];
    }

    /**
     * Выполнить получение новых замеров и записать полученные данные в хранилище
     */
    public void updateMeasurement() {
        log.debug("updateMeasurement() called");
        if (getInitializationState() == InitializationState.NOT_INITIALIZED){
            log.warn("updateMeasurement: module have initialization error! Pass...");
            return;
        }
        try {
            measurementData = reader.read(getAddress(), readerArgs);
            if (dataOutput == null) {
                return;
            }
            for (int i = 0; i < measurementData.length; i++) {
                dataOutput.write(measurementData[i], measurementIDs[i]);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            measurementData = new MeasurementData[0];
        }
    }
}

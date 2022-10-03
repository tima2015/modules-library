package ru.funnydwarf.iot.ml.sensor.dataio;

import ru.funnydwarf.iot.ml.sensor.MeasurementData;

import java.io.IOException;

/**
 * Интерфейс вывода данных замеров в хранилище
 */
public interface DataOutput {

    /**
     * Вывод данных замера в хранилище
     * @param data данные замера для записи
     * @param measurementId идентификатор замера
     */
    void write(MeasurementData data, String measurementId) throws IOException;
    
}

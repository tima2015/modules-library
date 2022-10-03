package ru.funnydwarf.iot.ml.sensor.dataio;

import ru.funnydwarf.iot.ml.sensor.MeasurementData;

import java.io.IOException;

/**
 * Интерфейс получения данных замеров из хранилища
 */
public interface DataInput {

    /**
     * Получение данных замеров.
     * В случае если данных меньше чем запрошено, будет возращено сколько удалось найти.
     * Если отступ превышает количество данных, то возвращается пустой массив.
     *
     * @param measurementId   идентификатор замера
     * @param measurementName название замера
     * @param unit            единицы измерения данных замера
     * @param offset          отступ от самого последнего значения
     * @param length          количество запрашиваемых значений
     * @return массив данных замеров размером <= length
     * @see MeasurementData
     */
    MeasurementData[] read(String measurementId, String measurementName, String unit, int offset, int length) throws IOException;

}

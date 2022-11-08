package ru.funnydwarf.iot.ml.sensor.reader;

import ru.funnydwarf.iot.ml.sensor.MeasurementData;

public interface Reader {
    /**
     * Прочитать показания датчиков устройства
     * @param address адрес датчика
     * @return массив замеров для всех датчиков устройства
     */
    MeasurementData[] read(Object address, Object ... args);

    /**
     * @return шаблон массива замеров без актуальных данных с датчиков
     */
    MeasurementData[] getTemplateRead();

}

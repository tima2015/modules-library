package ru.funnydwarf.iot.ml.sensor.reader;

public interface Reader {
    /**
     * Прочитать показания датчиков устройства
     * @param address адрес датчика
     * @return массив замеров
     */
    double[] read(Object address, Object ... args);

}

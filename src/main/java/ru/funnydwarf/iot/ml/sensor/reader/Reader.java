package ru.funnydwarf.iot.ml.sensor.reader;

public interface Reader<AddressT> {
    /**
     * Прочитать показания датчиков устройства
     * @param address адрес датчика
     * @return массив замеров
     */
    double[] read(AddressT address, Object ... args);

}

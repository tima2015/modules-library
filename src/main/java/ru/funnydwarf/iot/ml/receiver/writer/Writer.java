package ru.funnydwarf.iot.ml.receiver.writer;

public interface Writer {
    /**
     * Передать значение модулю
     * @param address адрес модуля
     * @param value значение которое необходимо передать модулю
     */
    void write(Object address, Object value);
}

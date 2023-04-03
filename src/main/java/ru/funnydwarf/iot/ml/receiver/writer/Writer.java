package ru.funnydwarf.iot.ml.receiver.writer;

public interface Writer<AddressT, ValueT> {
    /**
     * Передать значение модулю
     * @param address адрес модуля
     * @param value значение которое необходимо передать модулю
     */
    void write(AddressT address, ValueT value);
}

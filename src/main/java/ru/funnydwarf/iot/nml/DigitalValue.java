package ru.funnydwarf.iot.nml;

/**
 * Значения для цифрового ввода/вывода
 */
public enum DigitalValue {
    HIGH(1),
    LOW(0);

    public final int value;

    DigitalValue(int value) {
        this.value = value;
    }
}

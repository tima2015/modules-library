package ru.funnydwarf.iot.nml;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * Перечисление типов пинов
 * GND - земля/gnd
 * POWER_3_3V - питание 3.3v
 * POWER_5V - питание 5v
 * GPIO - интерфейс ввода/вывода
 */
@JsonAutoDetect
public enum PinType {
    GND,
    POWER_3_3V,
    POWER_5V,
    GPIO
}

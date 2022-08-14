package ru.funnydwarf.iot.nml;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * Текущее состояние пинов
 * NONE - Отсутствие состояние, применяется к пинам земли и питания
 * UNKNOWN - Состояние неизвестно, возникает при инициализации пинов ввода/вывода, проведении нативных операций,
 * требующих изменения состояния пина
 * OUTPUT - Состояние вывода
 * INPUT - Состояние ввода
 * Поле value содержит численую интерпритацию перечисления
 */
@JsonAutoDetect
public enum PinMode {
    NONE(-2),
    UNKNOWN(-1),
    INPUT(0),
    OUTPUT(1),
    PWM_OUTPUT(2),
    GPIO_CLOCK(3),
    SOFT_PWM_OUTPUT(4),
    SOFT_TONE_OUTPUT(5),
    PWM_TONE_OUTPUT(6);

    public final int code;

    PinMode(int code) {
        this.code = code;
    }
}

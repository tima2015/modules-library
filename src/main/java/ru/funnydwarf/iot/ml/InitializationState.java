package ru.funnydwarf.iot.ml;

/**
 * Состояние инициализации
 */
public enum InitializationState {
    /**
     * Не подключено
     */
    NOT_CONNECTED,
    /**
     * Начальное состояние
     */
    NOT_INITIALIZED,
    /**
     * Ошибка инициализации
     */
    INITIALIZATION_ERROR,
    /**
     * Инициализация успешна
     */
    OK
}

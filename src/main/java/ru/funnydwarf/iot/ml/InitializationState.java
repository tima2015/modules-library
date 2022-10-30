package ru.funnydwarf.iot.ml;

/**
 * Состояние инициализации
 */
public enum InitializationState {
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

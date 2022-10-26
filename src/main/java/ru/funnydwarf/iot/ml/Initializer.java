package ru.funnydwarf.iot.ml;

public interface Initializer {
    /**
     * Выполнение необходимых для инициализации действий
     *
     * @throws Exception исключение возникшее при инициализации
     */
    void initialize(ModuleGroup group) throws Exception;
}

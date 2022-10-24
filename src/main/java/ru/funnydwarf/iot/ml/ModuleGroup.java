package ru.funnydwarf.iot.ml;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Группа модулей
 */
public abstract class ModuleGroup {

    private final Logger log;

    /**
     * Состояние инициализации групп модулей
     */
    enum State {
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

    /**
     * Состояние инициализации группы
     */
    private State state = State.NOT_INITIALIZED;

    /**
     * Имя группы
     */
    private final String name;
    /**
     * Описание группы
     */
    private final String description;

    public ModuleGroup(String name, String description) {
        log = LoggerFactory.getLogger(name);
        this.name = name;
        this.description = description;
    }

    /**
     * Выполнить инициализацию
     */
    public final void initialize() {
        try {
            doInit();
            state = State.OK;
        } catch (Exception e) {
            state = State.INITIALIZATION_ERROR;
            log.error(e.getMessage(), e);
            throw  new RuntimeException(e);
        }
    }

    /**
     * Выполнение необходимых для инициализации действий
     * @throws Exception исключение возникшее при инициализации
     */
    protected abstract void doInit() throws Exception;
    
    public State getState() {
        return state;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }


}

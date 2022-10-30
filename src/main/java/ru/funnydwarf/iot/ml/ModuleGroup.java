package ru.funnydwarf.iot.ml;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

/**
 * Группа модулей
 */
public abstract class ModuleGroup implements InitializingBean {

    private final Logger log;

    /**
     * Состояние инициализации групп модулей
     */
    public enum State {
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

    @Override
    public void afterPropertiesSet() {
        log.debug("afterPropertiesSet() called");
        if (state != State.NOT_INITIALIZED) {
            log.warn("afterPropertiesSet: ModuleGroup = [{}] already init! Pass...", this.name);
            return;
        }
        try {
            state = initialize();
        } catch (Exception e) {
            state = State.INITIALIZATION_ERROR;
            log.error(e.getMessage(), e);
        } finally {
            log.info("afterPropertiesSet: {}", state.name());
        }
    }

    /**
     * Инициализация группы модулей
     */
    protected abstract State initialize() throws Exception;

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

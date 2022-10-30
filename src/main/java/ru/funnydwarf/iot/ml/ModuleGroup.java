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
     * Состояние инициализации группы
     */
    private InitializationState initializationState = InitializationState.NOT_INITIALIZED;

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
        if (initializationState != InitializationState.NOT_INITIALIZED) {
            log.warn("afterPropertiesSet: ModuleGroup = [{}] already init! Pass...", this.name);
            return;
        }
        try {
            initializationState = initialize();
        } catch (Exception e) {
            initializationState = InitializationState.INITIALIZATION_ERROR;
            log.error(e.getMessage(), e);
        } finally {
            log.info("afterPropertiesSet: {}", initializationState.name());
        }
    }

    /**
     * Инициализация группы модулей
     */
    protected abstract InitializationState initialize() throws Exception;

    public InitializationState getInitializationState() {
        return initializationState;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }


}

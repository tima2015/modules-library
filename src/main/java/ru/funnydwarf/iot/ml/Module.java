package ru.funnydwarf.iot.ml;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Properties;

/**
 * Класс модуля
 */
public abstract class Module implements InitializingBean {

    private final Logger log;

    /**
     * Группа к которой относится данный модуль
     */
    private final ModuleGroup group;

    /**
     * Каждый модуль в каком либо представлении имеет свой адрес
     */
    private final Object address;

    /**
     * Имя модуля
     */
    private final String name;

    /**
     * Описание модуля
     */
    private final String description;

    /**
     * Состояние инициализации модуля
     */
    private InitializationState initializationState = InitializationState.NOT_INITIALIZED;

    private final Properties properties = new Properties();

    public Module(ModuleGroup group,
                  Object address,
                  String name,
                  String description) {
        log = LoggerFactory.getLogger(name);
        this.group = group;
        this.address = address;
        this.name = name;
        this.description = description;
        loadProperties();
    }

    private void loadProperties() {
        File propertiesFile = new File("properties/%s.xml".formatted(name));
        if (!propertiesFile.exists()) {
            log.debug("loadProperties: Module does not have a settings file");
            return;
        }
        try {
            properties.loadFromXML(new FileInputStream(propertiesFile));
        } catch (IOException e) {
            log.warn("loadProperties: can't load properties!");
            log.warn(e.getMessage(), e);
        }
    }

    @Override
    public void afterPropertiesSet() {
        log.debug("afterPropertiesSet() called");
        if (initializationState != InitializationState.NOT_INITIALIZED) {
            log.warn("afterPropertiesSet: Module already init! Pass...");
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
     * Инициализация модуля
     */
    protected InitializationState initialize() throws Exception {
        return InitializationState.OK;
    }

    public ModuleGroup getGroup() {
        return group;
    }

    public Object getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public InitializationState getInitializationState() {
        return initializationState;
    }

    public Properties getProperties() {
        return properties;
    }
}

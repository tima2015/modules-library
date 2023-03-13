package ru.funnydwarf.iot.ml;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.lang.Nullable;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 * Класс модуля
 */
@Getter
@Slf4j
public abstract class Module implements InitializingBean {

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

    @Getter(AccessLevel.NONE)
    private final Initializer initializer;

    private final Properties properties = new Properties();

    public Module(ModuleGroup group,
                  Object address,
                  String name,
                  String description,
                  @Nullable Initializer initializer) {
        this.group = group;
        this.address = address;
        this.name = name;
        this.description = description;
        this.initializer = initializer;
        loadProperties();
    }

    private void loadProperties() {
        File propertiesFile = new File("properties/%s.xml".formatted(name));
        if (!propertiesFile.exists()) {
            log.debug("[{}] loadProperties: Module does not have a settings file", name);
            return;
        }
        try {
            properties.loadFromXML(new FileInputStream(propertiesFile));
        } catch (IOException e) {
            log.warn("[{}] loadProperties: can't load properties!", name);
            log.warn(e.getMessage(), e);
        }
    }

    @Override
    public void afterPropertiesSet() {
        log.debug("afterPropertiesSet() called");
        if (initializationState != InitializationState.NOT_INITIALIZED) {
            log.warn("[{}] afterPropertiesSet: Module already init! Pass...", name);
            return;
        }
        try {
            initializationState = initialize();
        } catch (Exception e) {
            initializationState = InitializationState.INITIALIZATION_ERROR;
            log.error("[{}] {}",name, e.getMessage(), e);
        } finally {
            log.info("[{}] afterPropertiesSet: {}", name, initializationState.name());
        }
    }

    /**
     * Инициализация модуля
     */
    protected InitializationState initialize() throws Exception {
        if (group instanceof ModuleListReadable) {
            List<Object> moduleList = ((ModuleListReadable) group).readModuleAdressesList();
            if (!moduleList.equals(address)) {
                return InitializationState.NOT_CONNECTED;
            }
        }
        return initializer != null ? initializer.initialize(this) : InitializationState.OK;
    }

    public static interface Initializer {
        InitializationState initialize(Module module);
    }

}

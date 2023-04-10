package ru.funnydwarf.iot.ml;

import jakarta.persistence.Entity;
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
public abstract class Module<ModuleGroupT extends ModuleGroup, AddressT> implements InitializingBean {

    /**
     * Группа к которой относится данный модуль
     */
    private final ModuleGroupT group;

    /**
     * Каждый модуль в каком либо представлении имеет свой адрес
     */
    private final AddressT address;

    private final ModuleDescription moduleDescription;

    /**
     * Состояние инициализации модуля
     */
    private InitializationState initializationState = InitializationState.NOT_INITIALIZED;

    @Getter(AccessLevel.NONE)
    private final Initializer initializer;

    private final Properties properties = new Properties();

    public Module(ModuleGroupT group,
                  AddressT address,
                  ModuleDescription moduleDescription,
                  @Nullable Initializer initializer) {
        this.group = group;
        this.address = address;
        this.moduleDescription = moduleDescription;
        this.initializer = initializer;
        loadProperties();
    }

    private void loadProperties() {
        File propertiesFile = new File("properties/%s.xml".formatted(moduleDescription.getName()));
        if (!propertiesFile.exists()) {
            log.debug("[{}] loadProperties: Module does not have a settings file", moduleDescription.getName());
            return;
        }
        try {
            properties.loadFromXML(new FileInputStream(propertiesFile));
        } catch (IOException e) {
            log.warn("[{}] loadProperties: can't load properties!", moduleDescription.getName());
            log.warn(e.getMessage(), e);
        }
    }

    @Override
    public void afterPropertiesSet() {
        log.debug("afterPropertiesSet() called");
        if (initializationState != InitializationState.NOT_INITIALIZED) {
            log.warn("[{}] afterPropertiesSet: Module already init! Pass...", moduleDescription.getName());
            return;
        }
        try {
            initializationState = initialize();
        } catch (Exception e) {
            initializationState = InitializationState.INITIALIZATION_ERROR;
            log.error("[{}] {}", moduleDescription.getName(), e.getMessage(), e);
        } finally {
            log.info("[{}] afterPropertiesSet: {}", moduleDescription.getName(), initializationState.name());
        }
    }

    /**
     * Инициализация модуля
     */
    protected InitializationState initialize() throws Exception {
        if (group instanceof ModuleListReadable<?>) {
            List<?> moduleList = ((ModuleListReadable<?>) group).readModuleAdressesList();
            if (!moduleList.contains(address)) {
                log.warn("[{}] initialize: module not connected!", moduleDescription.getName());
                return InitializationState.NOT_CONNECTED;
            }
        }
        if (initializer != null) {
            InitializationState state = initializer.initialize(this);
            if (state != InitializationState.OK) {
                log.warn("[{}] initialize: module have initialization error!", moduleDescription.getName());
            }
            return state;
        }
        return InitializationState.OK;
    }

    public interface Initializer {
        InitializationState initialize(Module<?,?> module);
    }

}

package ru.funnydwarf.iot.ml.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.funnydwarf.iot.ml.Module;
import ru.funnydwarf.iot.ml.InitializationState;
import ru.funnydwarf.iot.ml.task.command.TaskCommand;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Класс для реализации выполнения действий модулей при определённых событиях
 *
 * @param <T> класс модулей
 */
public class Task<T extends Module> {

    private final Logger log;
    private final String name;
    private final String description;
    private final boolean repeat;
    private boolean disable;
    private Date lastDone = null;
    /**
     * Действие с модулями, которое должно произойти при событии активировавшем триггер
     */
    private final TaskCommand<T> listener;
    private final List<T> modules;

    public Task(String name, String description, boolean repeat, boolean disable, TaskCommand<T> listener, List<T> modules) {
        log = LoggerFactory.getLogger(name);
        this.name = name;
        this.description = description;
        this.repeat = repeat;
        this.disable = disable;
        this.listener = listener;
        this.modules = modules;
    }

    public void doTask() {
        log.debug("doTask() called");
        if (disable) {
            log.debug("doTask: task disable! Pass...");
            return;
        }
        if (!repeat && lastDone != null) {
            log.debug("doTask: task to be completed once and has already been completed! Pass...");
            return;
        }
        for (T module : modules) {
            if (module.getGroup().getInitializationState() == InitializationState.OK) {
                listener.onDoTask(module);
            }
            log.debug("doTask: ModuleGroup = [{}] have initialization error! Module = [{}] passed...",
                    module.getGroup().getName(), module.getName());
        }
        lastDone = new Date();
        log.debug("doTask: task complete! New lastDone = [{}]", lastDone);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isRepeat() {
        return repeat;
    }

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    /**
     * @return Время последнего выполнения задания или null если задание ещё не выполнялось. После инициализации считается что задание не выполнялось!
     */
    public Date getLastDone() {
        return lastDone;
    }

    /**
     * @return неизменяемый список модулей обрабатываемых триггером
     */
    public List<T> getModules() {
        return Collections.unmodifiableList(modules);
    }
}

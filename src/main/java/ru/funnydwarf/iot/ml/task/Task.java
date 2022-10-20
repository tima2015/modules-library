package ru.funnydwarf.iot.ml.task;

import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import ru.funnydwarf.iot.ml.Module;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.Executors;

/**
 * Класс для реализации выполнения действий модулей при определённых событиях
 * @param <T> класс модулей
 */
public class Task<T extends Module> {

    private final String name;
    private final String description;
    /**
     * Действие с модулями, которое должно произойти при событии активировавшем тригер
     */
    private final TaskCommand listener;

    private final TaskTriggerType type;
    private final List<T> modules;
    private final TaskTimer taskTimer;

    public Task(String name, String description, TaskCommand listener, TaskTriggerType triggerType, List<T> modules, TaskTimer taskTimer) {
        this.name = name;
        this.description = description;
        this.listener = listener;
        this.modules = modules;
        this.type = triggerType;
        this.taskTimer = taskTimer;
    }

    private void onTrigger(){
        for (T module : modules) {
            listener.<T>onTaskTrigger(module);
        }
    }

    /**
     * @return неизменяемый список модулей обрабатываемых тригером
     */
    public List<T> getModules() {
        return Collections.unmodifiableList(modules);
    }

    public TaskTriggerType getType() {
        return type;
    }

    private class TaskTimer implements SchedulingConfigurer {

        private final long timeToRepeat;

        private TaskTimer(long timeToRepeat) {
            this.timeToRepeat = timeToRepeat;
        }

        @Override
        public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
            taskRegistrar.setScheduler(Executors.newSingleThreadScheduledExecutor());
            taskRegistrar.addTriggerTask(Task.this::onTrigger, triggerContext -> {
                Optional<Date> lastCompletionTime =
                        Optional.ofNullable(triggerContext.lastCompletionTime());
                Instant nextExecutionTime =
                        lastCompletionTime.orElseGet(Date::new).toInstant()
                                .plusMillis(timeToRepeat);
                return Date.from(nextExecutionTime);
            });
        }
    }
}

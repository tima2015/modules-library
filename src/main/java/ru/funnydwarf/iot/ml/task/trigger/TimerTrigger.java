package ru.funnydwarf.iot.ml.task.trigger;

import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import ru.funnydwarf.iot.ml.Module;
import ru.funnydwarf.iot.ml.task.Task;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;

/**
 * Циклический триггер по таймеру
 */
public class TimerTrigger extends Trigger implements SchedulingConfigurer {

    private final long timeToRepeat;

    public TimerTrigger(List<Task<? extends Module>> tasks, long timeToRepeat) {
        super(tasks);
        this.timeToRepeat = timeToRepeat;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(Executors.newSingleThreadScheduledExecutor());
        taskRegistrar.addTriggerTask(this::triggerAll, triggerContext -> {
            Instant instant = triggerContext.lastCompletion();
            Optional<Date> lastCompletionTime =
                    Optional.of(instant == null ? new Date(0) : Date.from(instant));
            Instant nextExecutionTime =
                    lastCompletionTime.orElseGet(Date::new).toInstant().plusMillis(timeToRepeat);
            return Date.from(nextExecutionTime).toInstant();
        });
    }
}

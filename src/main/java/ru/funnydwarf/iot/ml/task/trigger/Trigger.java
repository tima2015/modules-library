package ru.funnydwarf.iot.ml.task.trigger;

import ru.funnydwarf.iot.ml.Module;
import ru.funnydwarf.iot.ml.task.Task;

import java.util.Collections;
import java.util.List;

public abstract class Trigger {

    private final List<Task<? extends Module>> tasks;

    protected Trigger(List<Task<? extends Module>> tasks) {
        this.tasks = tasks;
    }

    public List<Task<? extends Module>> getTasks() {
        return Collections.unmodifiableList(tasks);
    }

    protected void triggerAll() {
        tasks.forEach(Task::doTask);
    }

}

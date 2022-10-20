package ru.funnydwarf.iot.ml.task;

import ru.funnydwarf.iot.ml.Module;

public interface TaskCommand {
    /**
     * Действие с модулем, которое должно произойти при событии активировавшем тригер
     * @param module обрабатываемый модуль
     * @param <T> класс модуля
     */
    <T extends Module> void onTaskTrigger(T module);
}

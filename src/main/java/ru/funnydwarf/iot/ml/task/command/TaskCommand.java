package ru.funnydwarf.iot.ml.task.command;

import ru.funnydwarf.iot.ml.Module;

public interface TaskCommand<T extends Module> {
    /**
     * Действие с модулем, которое должно произойти при событии активировавшем триггер
     * @param module обрабатываемый модуль
     */
    void onDoTask(T module);
}

package ru.funnydwarf.iot.ml.task.command;

import ru.funnydwarf.iot.ml.sensor.Sensor;

/**
 * Чтение простого датчика
 */
public class ReadSensorTaskCommand implements TaskCommand<Sensor> {
    @Override
    public void onDoTask(Sensor module) {
        module.takeMeasurement();
    }
}

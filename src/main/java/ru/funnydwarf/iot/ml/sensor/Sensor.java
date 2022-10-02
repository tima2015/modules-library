package ru.funnydwarf.iot.ml.sensor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import ru.funnydwarf.iot.ml.Module;
import ru.funnydwarf.iot.ml.sensor.reader.Reader;
import ru.funnydwarf.iot.ml.sensor.datawriter.DataIO;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.Executors;

public class Sensor extends Module implements SchedulingConfigurer {

    private final static Logger log = LoggerFactory.getLogger(Sensor.class);

    private MeasurementData[] measurementData = new MeasurementData[0];

    private final Reader reader;
    private final DataIO dataIO;
    private final long timeToRepeatMeasurement;

    public Sensor(Reader reader, DataIO dataIO, long timeToRepeatMeasurement, Object address, String name, String description) {
        super(address, name, description);
        this.reader = reader;
        this.dataIO = dataIO;
        this.timeToRepeatMeasurement = timeToRepeatMeasurement;
    }

    public Sensor(Reader reader, DataIO dataIO, long timeToRepeatMeasurement, Object address, String name, String description, String userCustomName, String userCustomDescription) {
        super(address, name, description, userCustomName, userCustomDescription);
        this.reader = reader;
        this.dataIO = dataIO;
        this.timeToRepeatMeasurement = timeToRepeatMeasurement;
    }

    public MeasurementData[] getMeasurementData() {
        return measurementData;
    }

    public MeasurementData[][] getHistoryMeasurementValue(int offset, int length){
        log.debug("getHistoryMeasurementValue() called with: offset = [{}], length = [{}]", offset, length);
        try {
            String[] units = new String[measurementData.length];
            for (int i = 0; i < units.length; i++) {
                units[i] = measurementData[i].unitName();
            }
            return dataIO.read(getName(),units, offset, length);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return new MeasurementData[0][0];
    }

    private void updateMeasurement() {
        log.debug("updateMeasurement() called");
        try {
            measurementData = reader.read(getAddress());
            if (dataIO != null){
                dataIO.write(measurementData, getName());
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            measurementData = new MeasurementData[0];
        }
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(Executors.newSingleThreadScheduledExecutor());
        taskRegistrar.addTriggerTask(this::updateMeasurement, triggerContext -> {
            Optional<Date> lastCompletionTime =
                    Optional.ofNullable(triggerContext.lastCompletionTime());
            Instant nextExecutionTime =
                    lastCompletionTime.orElseGet(Date::new).toInstant()
                            .plusMillis(timeToRepeatMeasurement);
            return Date.from(nextExecutionTime);
        });
    }
}
